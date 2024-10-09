package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.service.LearnService;
import ru.kvshe.languagetrainer.service.WordService;

import java.util.List;

@Controller
@RequestMapping("/learn")
@RequiredArgsConstructor
public class LearnController {
    private final WordService wordService;
    private final LearnService learnService;

    @Operation(
            summary = "Получить список слов",
            description = "Формирует список слов для обучения и возвращает страницу с проверкой первого слова"
    )
    @GetMapping
    public ModelAndView learn() {
        learnService.setWords(wordService.getAll());
        return new ModelAndView("learn/show")
                .addObject("word", learnService.getWords().getFirst());
    }

    @GetMapping("/{id}")
    public ModelAndView learnId(@PathVariable Long id) {
        List<Word> words = learnService.getWords();
        List<Word> errors = learnService.getErrors();

        if (!words.isEmpty()) {
            return new ModelAndView("learn/show", "word", words.getFirst());
        }

        if (!errors.isEmpty()) {
            return new ModelAndView("learn/show", "word", errors.getFirst());
        }

        return new ModelAndView("redirect:/words");
    }

    @PostMapping
    public ModelAndView learn(@ModelAttribute("word") Word word) {
        // todo проверка слова
        return new ModelAndView("redirect:/learn");
    }
}
