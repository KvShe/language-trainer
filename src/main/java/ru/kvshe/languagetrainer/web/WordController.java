package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kvshe.languagetrainer.exception.NotFoundWordException;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.service.UserService;
import ru.kvshe.languagetrainer.service.WordService;
import ru.kvshe.languagetrainer.util.API;
import ru.kvshe.languagetrainer.util.sort.EnglishSortStrategy;
import ru.kvshe.languagetrainer.util.sort.RussianSortStrategy;

import java.util.List;

@Tag(
        name = "Page Controller",
        description = "Возвращает страницы")
@Controller
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;
    private final UserService userService;

    @Operation(
            summary = "Get all Words",
            description = "Получить страницу со списком всех слов",
            responses = @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = Word.class))))
    @API.InternalServerError
    @GetMapping
    public ModelAndView show(@RequestParam(required = false) String sort) {
        String login = userService.getLoginCurrentUser();
        User user = userService.getUserByLogin(login);
        List<Word> words = wordService.getAllFor(user.getId());

        if (sort != null && sort.equals("english")) {
            wordService.setSortStrategy(new EnglishSortStrategy());
            wordService.sortWords(words);
        }

        if (sort != null && sort.equals("russian")) {
            wordService.setSortStrategy(new RussianSortStrategy());
            wordService.sortWords(words);
        }

        return new ModelAndView("words/index")
                .addObject("words", words);
    }

    @API.InternalServerError
    @GetMapping("/new")
    public ModelAndView showFormNew(Word word, Model model) {
        model.addAttribute(word);
        return new ModelAndView("words/new");
    }

    // create
    @API.InternalServerError
    @PostMapping("/new")
    public ModelAndView create(@ModelAttribute Word word) {
        // Валидация - проверка, что все введённые значения не пусты
        if (word.getEnglish().isEmpty() || word.getRussian().isEmpty()) {
            return new ModelAndView("redirect:/words/new");
        }

        wordService.save(word);
        return new ModelAndView("redirect:/words");
    }

    // read
    @API.InternalServerError
    @API.NotFoundResponse
    @GetMapping("/{id}")
    public ModelAndView showForm(@PathVariable Long id, Model model) {
//        Word word = wordService.getById(id);
        Word word = wordService.getById(id)
                .orElseThrow(() -> new NotFoundWordException(id));

        model.addAttribute("word", word);
        return new ModelAndView("words/show");
    }

    // edit-form
    @API.InternalServerError
    @GetMapping("/{id}/edit")
    public ModelAndView showFormEdit(@PathVariable Long id, Model model) {
//        Word word = wordService.getById(id);
        Word word = wordService.getById(id)
                .orElseThrow(() -> new NotFoundWordException(id));

        model.addAttribute(word);
        return new ModelAndView("words/edit");
    }

    // edit-action
    @API.InternalServerError
    @PatchMapping("/{id}")
    public ModelAndView update(@PathVariable Long id,
                               @ModelAttribute Word word) {
        wordService.update(id, word);
        return new ModelAndView("redirect:/words");
    }

    @API.InternalServerError
    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        wordService.deleteById(id);
        return new ModelAndView("redirect:/words");
    }
}
