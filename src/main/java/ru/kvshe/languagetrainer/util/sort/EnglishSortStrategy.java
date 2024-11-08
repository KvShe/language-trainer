package ru.kvshe.languagetrainer.util.sort;

import ru.kvshe.languagetrainer.model.Word;

import java.util.Comparator;
import java.util.List;

/**
 * Стратегия сортировки слов по английскому переводу.
 * Эта стратегия сортирует список слов в алфавитном порядке на основе поля 'english' (перевод на английский).
 */
public class EnglishSortStrategy implements SortStrategy {
    /**
     * Сортирует список слов по английскому переводу.
     *
     * @param words список слов, который необходимо отсортировать
     * @return отсортированный список слов
     */
    @Override
    public List<Word> sort(List<Word> words) {
        words.sort(Comparator.comparing(Word::getEnglish));
        return words;
    }
}
