package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.service.LearnService;

import java.util.List;

@Controller
@RequestMapping("/learn")
@RequiredArgsConstructor
public class LearnController {
    private final LearnService learnService;

    @Operation(
            summary = "Получить список слов",
            description = "Формирует список слов для обучения и возвращает страницу с проверкой первого слова"
    )
    @GetMapping
    public ModelAndView learn(Word word) {
        learnService.createListOfWords();
        word.setRussian(learnService.getWords().getFirst().getRussian());
        return new ModelAndView("learn/show")
                .addObject("word", word);
    }

    @GetMapping("/show")
    public ModelAndView learnShow(Word word) {
        List<Word> words = learnService.getWords();

        if (!words.isEmpty()) {
            word.setRussian(learnService.getWords().getFirst().getRussian());
            return new ModelAndView("learn/show", "word", words.getFirst());
        }

        return new ModelAndView("redirect:/words");
    }

    @PostMapping
    public ModelAndView checkLearn(@ModelAttribute Word word) {
        learnService.checkWord(word);

        // todo убрать System.out.println()
        System.out.println(word);
        return new ModelAndView("redirect:/learn/show");
    }
}
