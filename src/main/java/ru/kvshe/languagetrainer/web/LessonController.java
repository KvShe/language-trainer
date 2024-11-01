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
    public ModelAndView lesson(Word word) {
        List<Word> words = lessonService.createListOfRandomWords();// todo создавать список слов, которые давно не проверялись

        Collections.shuffle(words);

        word.setRussian(words.getFirst().getRussian());
        return new ModelAndView("lesson/show")
                .addObject("word", word)
                .addObject("progress", 0);
    }

    @Operation(
            summary = "Возвращает страницу с уроком",
            description = """
                    Получает список слов, которые проверятся
                    Если список не пуст - берёт первое слово и передаёт его на страницу с уроком
                    Если список пуст - возвращает страницу с завершением урока
                    """)
    @GetMapping("/show")
    public ModelAndView lessonShow(Word word) {
        List<Word> words = lessonService.getWords();

        if (!words.isEmpty()) {
            word.setRussian(lessonService.getWords().getFirst().getRussian());
            return new ModelAndView("lesson/show")
                    .addObject("word", word)
                    .addObject("progress", lessonService.getProgressPercentage(words.size()));
        }

        int result = lessonService.getPercentageOfCorrectAnswers();
        lessonService.clean();
        lessonService.updateWords();
        return new ModelAndView("lesson/win")
                .addObject("count", result)
                .addObject("progress", lessonService.getProgressPercentage(words.size()));
    }

    @PostMapping
    public ModelAndView checkLesson(@ModelAttribute Word word) {
        // protected от случайного нажатия Enter в пустом поле или поле, заполненным пробелами
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
    public ModelAndView translationWrongAnswer() {
        List<Word> words = lessonService.getWords();
        Word word = words.getLast();
        return new ModelAndView("lesson/wrong-answer", "word", word)
                .addObject("progress", lessonService.getProgressPercentage(words.size()));
    }

    @Operation(
            summary = "Верный перевод",
            description = "Возвращает страницу, сообщающую о верном переводе слова: correct-answer.html")
    @GetMapping("/correct-answer")
    public ModelAndView translationCorrectAnswer() {
        List<Word> words = lessonService.getWords();
        return new ModelAndView("lesson/correct-answer", "word", "Верно!")
                .addObject("progress", lessonService.getProgressPercentage(words.size()));
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
