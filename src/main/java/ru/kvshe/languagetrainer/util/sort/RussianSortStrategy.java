package ru.kvshe.languagetrainer.util.sort;

import ru.kvshe.languagetrainer.model.Word;

import java.util.Comparator;
import java.util.List;

/**
 * Стратегия сортировки слов по русскому переводу.
 * Эта стратегия сортирует список слов в алфавитном порядке на основе поля 'russian' (перевод на русский).
 */
public class RussianSortStrategy implements SortStrategy {
    /**
     * Сортирует список слов по русскому переводу.
     *
     * @param words список слов, который необходимо отсортировать
     * @return отсортированный список слов
     */
    @Override
    public List<Word> sort(List<Word> words) {
        words.sort(Comparator.comparing(Word::getRussian));
        return words;
    }
}
