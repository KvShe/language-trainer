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
import ru.kvshe.languagetrainer.service.WordService;

import java.util.Collections;
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
        List<Word> words = learnService.getWords();
        learnService.createListOfWords(); // todo создавать список слов, которые давно не проверялись

        Collections.shuffle(learnService.getWords());

        word.setRussian(learnService.getWords().getFirst().getRussian());
        return new ModelAndView("learn/show")
                .addObject("word", word);
    }

    @GetMapping("/show")
    public ModelAndView learnShow(Word word) {
        List<Word> words = learnService.getWords();

        if (!words.isEmpty()) {
            word.setRussian(learnService.getWords().getFirst().getRussian());
//            word.setEnglish(null);
//            return new ModelAndView("learn/show", "word", words.getFirst());
            return new ModelAndView("learn/show", "word", word);
        }

//        return new ModelAndView("redirect:/words");
        return new ModelAndView("redirect:/learn/win");
    }

    @PostMapping
    public ModelAndView checkLearn(@ModelAttribute Word word) {
        // todo проверить необходимость этого if
//        if (word == null) {
//            return new ModelAndView("redirect:/words");
//        }

        if (!learnService.checkWord(word)) {
            return new ModelAndView("redirect:/learn/wrong-answer");
        } else {
            return new ModelAndView("redirect:/learn/correct-answer");
        }

    }

    /**
     * Возвращает страницу, сообщающую о неверном переводе слова
     *
     * @return wrong-answer.html
     */
    @GetMapping("/wrong-answer")
    public ModelAndView translationError() {
        // fixme поле word.english - писать с заглавной буквы

        Word word = learnService.getWords().getLast();
        System.out.println(word);
        return new ModelAndView("learn/wrong-answer", "word", word);
    }

    @GetMapping("/correct-answer")
    public ModelAndView translationCorrectAnswer() {
        return new ModelAndView("learn/correct-answer", "word", "Верно!");
    }

    @GetMapping("/win")
    public ModelAndView showWin() {
        return new ModelAndView("learn/win");
    }
}
