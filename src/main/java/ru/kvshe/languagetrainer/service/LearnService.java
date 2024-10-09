package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;

import java.util.List;

@Service
@Getter
@Setter
public class LearnService {
    private List<Word> words;
    private List<Word> errors;

    // todo реализовать метод проверки слова
    public void checkWord(Word word) {

    }
}
