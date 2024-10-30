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
    private List<Word> wordsToUpdateInDatabase = new ArrayList<>();

    // variables for LessonObserver
    private int correctAnswers;
    private int wrongAnswers;
    private int quantity = 10;

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
            wordsToUpdateInDatabase.add(word);
        } else {
            words.add(word);
        }

        words.removeFirst();

        return result;
    }

    /**
     * Обновляет у проверяемых слов поле lastUsed
     */
    public void updateWords() {
        wordService.updateWordsData(wordsToUpdateInDatabase);
        wordsToUpdateInDatabase.clear();
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

    /**
     * Метод возвращает процент прогресса прохождения урока
     *
     * @param quantityWords количество слов, которые осталось проверить
     * @return процент прогресса прохождения урока
     */
    @Override
    public int getProgressPercentage(int quantityWords) {
        int progress = (int) (words.size() * 100f / quantity);
        return 100 - progress;
    }
}
