package ru.kvshe.languagetrainer.util.sort;

import ru.kvshe.languagetrainer.model.Word;

import java.util.List;

public interface SortStrategy {
    List<Word> sort(List<Word> words);
}
