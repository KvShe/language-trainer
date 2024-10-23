package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class LearnService {
    private final WordService wordService;
    private List<Word> words = new ArrayList<>();
//    private List<Word> errors;

    public void createListOfWords() {
        words = wordService.getAll();
    }

    public boolean checkWord(Word word) {
        // переводит строки в нижний регистр, убирает все символы, кроме латиницы и сравнивает
        boolean result = util(word).getEnglish().equals(words.getFirst().getEnglish().toLowerCase());

        if (!result) {
            word = words.getFirst();
            words.add(word);
        }

        words.removeFirst();

        return result;
    }

    private Word util(Word word) {
        word.setEnglish(
                word.getEnglish()
                        .toLowerCase()
                        .replaceAll("[^a-z]", "")
        );

        return word;
    }
}
