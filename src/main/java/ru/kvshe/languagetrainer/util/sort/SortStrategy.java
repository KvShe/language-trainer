package ru.kvshe.languagetrainer.util.sort;

import ru.kvshe.languagetrainer.model.Word;

import java.util.List;

/**
 * Интерфейс для стратегий сортировки слов.
 * Этот интерфейс предоставляет контракт для различных стратегий сортировки списка слов.
 * Каждая реализация этого интерфейса должна реализовать метод сортировки в соответствии с определённой логикой.
 */
public interface SortStrategy {
    /**
     * Сортирует список слов.
     *
     * @param words список слов, который необходимо отсортировать
     * @return отсортированный список слов
     */
    List<Word> sort(List<Word> words);
}
