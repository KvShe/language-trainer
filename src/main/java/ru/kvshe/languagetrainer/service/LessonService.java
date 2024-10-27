package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class LessonService implements LessonObserver {
    private final WordService wordService;
    private List<Word> words = new ArrayList<>();

    // variables for LessonObserver
    private int correctAnswers;
    private int wrongAnswers;

    public List<Word> createListOfRandomWords() {
        words = wordService.getRandomWords();
        return words;
    }

    public boolean checkWord(Word word) {
        // Сравнивает строки english
        boolean result = clearAwayDebris(word).getEnglish().equals(words.getFirst().getEnglish().toLowerCase());

        word = words.getFirst();
        if (result) {
            word.setLastUsed(LocalDate.now());
//            wordService.updateLastUsed(word);
        } else {
            words.add(word);
        }

        words.removeFirst();

        return result;
    }

    /**
     * Переводит значение поля english в нижний регистр, убирает все символы, кроме латиницы
     *
     * @param word слово со значениями, полученными от пользователя
     * @return слово с полем english в нижнем регистре и состоящее только из латиницы
     */
    private Word clearAwayDebris(Word word) {
        word.setEnglish(
                word.getEnglish()
                        .toLowerCase()
                        .replaceAll("[^a-z]", "")
        );

        return word;
    }

    @Override
    public void increaseCorrectAnswers() {
        ++correctAnswers;
    }

    @Override
    public void increaseWrongAnswers() {
        ++wrongAnswers;
    }

    @Override
    public int getPercentageOfCorrectAnswers() {
        double result = ((double) correctAnswers / (correctAnswers + wrongAnswers)) * 100;
        return (int) result;
    }

    @Override
    public void clean() {
        correctAnswers = 0;
        wrongAnswers = 0;
    }
}
