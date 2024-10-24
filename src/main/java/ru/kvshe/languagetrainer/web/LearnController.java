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

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/learn")
@RequiredArgsConstructor
public class LearnController {
    private final LearnService learnService;
    private int correctAnswers;
    private int wrongAnswers;

    @Operation(
            summary = "Получить список слов",
            description = "Формирует список слов для обучения и возвращает страницу с проверкой первого слова")
    @GetMapping
    public ModelAndView learn(Word word) {
        List<Word> words = learnService.createListOfWords();// todo создавать список слов, которые давно не проверялись

        Collections.shuffle(words);

        word.setRussian(words.getFirst().getRussian());
        return new ModelAndView("learn/show")
                .addObject("word", word);
    }

    @GetMapping("/show")
    public ModelAndView learnShow(Word word) {
        List<Word> words = learnService.getWords();

        if (!words.isEmpty()) {
            word.setRussian(learnService.getWords().getFirst().getRussian());
            return new ModelAndView("learn/show")
                    .addObject("word", word);
        }

        // fixme оптимизировать возвращение на page эффективность прохождения урока в %: quantity слов & quantity попыток
        float result = ((float) correctAnswers / (wrongAnswers + correctAnswers)) * 100;

        correctAnswers = 0;
        wrongAnswers = 0;
        return new ModelAndView("learn/win")
                .addObject("count", (int) result);
    }

    @PostMapping
    public ModelAndView checkLearn(@ModelAttribute Word word) {
        // todo проверить необходимость этого if
//        if (word == null) {
//            return new ModelAndView("redirect:/words");
//        }

        // protected от случайного нажатия Enter в пустом поле
        if (word.getEnglish().isEmpty()) {
            return new ModelAndView("redirect:/learn/show");
        }

        if (!learnService.checkWord(word)) {
            wrongAnswers++;
            return new ModelAndView("redirect:/learn/wrong-answer");
        } else {
            correctAnswers++;
            return new ModelAndView("redirect:/learn/correct-answer");
        }
    }

    @Operation(
            summary = "Не верный перевод",
            description = "Возвращает страницу, сообщающую о неверном переводе слова: wrong-answer.html")
    @GetMapping("/wrong-answer")
    public ModelAndView translationError() {
        Word word = learnService.getWords().getLast();
        System.out.println(word);
        return new ModelAndView("learn/wrong-answer", "word", word);
    }

    @Operation(
            summary = "Верный перевод",
            description = "Возвращает страницу, сообщающую о верном переводе слова: correct-answer.html")
    @GetMapping("/correct-answer")
    public ModelAndView translationCorrectAnswer() {
        return new ModelAndView("learn/correct-answer", "word", "Верно!");
    }

    @Operation(
            summary = "Успешное прохождение урока",
            description = "Возвращает страницу, сообщающую об успешном прохождении урока: win.html")
    @GetMapping("/win")
    public ModelAndView showWin() {
        return new ModelAndView("learn/win");
    }
}
