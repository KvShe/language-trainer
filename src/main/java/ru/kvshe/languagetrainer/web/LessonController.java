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
import ru.kvshe.languagetrainer.service.LessonService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @Operation(
            summary = "Получить список слов",
            description = "Формирует список слов для обучения и возвращает страницу с проверкой первого слова")
    @GetMapping
    public ModelAndView learn(Word word) {
        List<Word> words = lessonService.createListOfRandomWords();// todo создавать список слов, которые давно не проверялись

        Collections.shuffle(words);

        word.setRussian(words.getFirst().getRussian());
        return new ModelAndView("lesson/show")
                .addObject("word", word);
    }

    /**
     * @param word
     * @return
     */
    @GetMapping("/show")
    public ModelAndView learnShow(Word word) {
        List<Word> words = lessonService.getWords();

        if (!words.isEmpty()) {
            word.setRussian(lessonService.getWords().getFirst().getRussian());
            return new ModelAndView("lesson/show")
                    .addObject("word", word);
        }

        // fixme оптимизировать возвращение на page эффективность прохождения урока в %: quantity слов & quantity попыток
        int result = lessonService.getPercentageOfCorrectAnswers();
        lessonService.clean();
        lessonService.updateWords();
        return new ModelAndView("lesson/win")
                .addObject("count", result);
    }

    @PostMapping
    public ModelAndView checkLearn(@ModelAttribute Word word) {
        // todo проверить необходимость этого if
//        if (word == null) {
//            return new ModelAndView("redirect:/words");
//        }

        // protected от случайного нажатия Enter в пустом поле или поле, заполненными пробелами
        if (word.getEnglish().replaceAll(" ", "").isEmpty()) {
            return new ModelAndView("redirect:/lesson/show");
        }

        if (lessonService.checkWord(word)) {
            lessonService.increaseCorrectAnswers();
            return new ModelAndView("redirect:/lesson/correct-answer");
        } else {
            lessonService.increaseWrongAnswers();
            return new ModelAndView("redirect:/lesson/wrong-answer");
        }
    }

    @Operation(
            summary = "Не верный перевод",
            description = "Возвращает страницу, сообщающую о неверном переводе слова: wrong-answer.html")
    @GetMapping("/wrong-answer")
    public ModelAndView translationError() {
        Word word = lessonService.getWords().getLast();
        System.out.println(word);
        return new ModelAndView("lesson/wrong-answer", "word", word);
    }

    @Operation(
            summary = "Верный перевод",
            description = "Возвращает страницу, сообщающую о верном переводе слова: correct-answer.html")
    @GetMapping("/correct-answer")
    public ModelAndView translationCorrectAnswer() {
        return new ModelAndView("lesson/correct-answer", "word", "Верно!");
    }

//    @Operation(
//            summary = "Успешное прохождение урока",
//            description = "Возвращает страницу, сообщающую об успешном прохождении урока: win.html")
//    @GetMapping("/win")
//    public ModelAndView showWin() {
//        System.out.println("Hello!");
//        lessonService.updateWords();
//        return new ModelAndView("lesson/win");
//    }
}
