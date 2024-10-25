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
public class LearnService {
    private final WordService wordService;
    private List<Word> words = new ArrayList<>();
//    private List<Word> errors;

    public List<Word> createListOfWords() {
//        words = wordService.getAll();
        words = wordService.getRandomWords();
        return words;
    }

    public boolean checkWord(Word word) {
        // Сравнивает строки english
        boolean result = clearAwayDebris(word).getEnglish().equals(words.getFirst().getEnglish().toLowerCase());

        word = words.getFirst();
        if (result) {
            word.setLastUsed(LocalDate.now());
//            wordService.update(word.getId(), word);
            wordService.updateLastUsed(word);
        } else {
            words.add(word);
        }

        words.removeFirst();

        return result;
    }

    /**
     * Переводит значение поля english в нижний регистр, убирает все символы, кроме латиницы
     *
     * @param word
     * @return
     */
    private Word clearAwayDebris(Word word) {
        word.setEnglish(
                word.getEnglish()
                        .toLowerCase()
                        .replaceAll("[^a-z]", "")
        );

        return word;
    }
}
