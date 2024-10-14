package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;

import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class LearnService {
    private final WordService wordService;
    private List<Word> words;
//    private List<Word> errors;

    public void createListOfWords() {
        words = wordService.getAll();
    }

    public void checkWord(Word word) {
        if (!words.getFirst().getEnglish().equals(word.getEnglish())) {
            word = words.getFirst();
            words.add(word);
        }

        words.removeFirst();
    }
}
