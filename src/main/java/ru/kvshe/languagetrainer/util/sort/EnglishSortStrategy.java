package ru.kvshe.languagetrainer.util.sort;

import ru.kvshe.languagetrainer.model.Word;

import java.util.Comparator;
import java.util.List;

public class EnglishSortStrategy implements SortStrategy {
    @Override
    public List<Word> sort(List<Word> words) {
        words.sort(Comparator.comparing(Word::getEnglish));
        return words;
    }
}
