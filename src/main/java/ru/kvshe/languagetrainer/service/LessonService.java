package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления уроками. Реализует интерфейс {@link LessonObserver}, чтобы отслеживать статистику прохождения уроков.
 * Этот сервис управляет списками слов для урока, проверяет правильность ответов, обновляет данные о словах в базе данных и рассчитывает прогресс.
 */
@Service
@Getter
@Setter
@RequiredArgsConstructor
public class LessonService implements LessonObserver {
    private final WordService wordService;
    private List<Word> words = new ArrayList<>();
    private List<Word> wordsToUpdateInDatabase = new ArrayList<>();

    /**
     * variables for LessonObserver
     * Переменные для отслеживания статистики правильных и неправильных ответов
     */
    private int correctAnswers;
    private int wrongAnswers;
    private int quantity; // количество слов в загруженном уроке

    /**
     * Создаёт список случайных слов для урока.
     *
     * @return список случайных слов
     */
    public List<Word> createListOfRandomWords() {
        words = wordService.getRandomWords();
        return words;
    }

    /**
     * Создаёт список забытых слов (не использовавшихся определённое время).
     *
     * @return список забытых слов
     */
    public List<Word> getForgottenWords() {
        words = wordService.getForgottenWords();
        return words;
    }

    /**
     * Проверяет слово пользователя на соответствие с текущим словом из урока.
     * Если слово правильное, обновляется дата последнего использования слова.
     * Если слово неправильное, оно добавляется обратно в список.
     *
     * @param word слово, которое пользователь ввёл
     * @return true, если слово верное, иначе false
     */
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
     * Обновляет поле lastUsed у проверенных слов в базе данных.
     */
    public void updateWords() {
        wordService.updateWordsData(wordsToUpdateInDatabase);
        wordsToUpdateInDatabase.clear();
    }

    /**
     * Преобразует значение поля english в нижний регистр и убирает все символы, кроме латиницы.
     *
     * @param word слово, которое необходимо обработать
     * @return обработанное слово с английскими символами в нижнем регистре
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
     * Вычисляет процент прогресса прохождения урока на основе количества оставшихся слов.
     *
     * @param quantityWords количество слов, которые ещё нужно проверить
     * @return процент прогресса урока
     */
    @Override
    public int getProgressPercentage(int quantityWords) {
        int progress = (int) (words.size() * 100f / quantity);
        return 100 - progress;
    }
}
