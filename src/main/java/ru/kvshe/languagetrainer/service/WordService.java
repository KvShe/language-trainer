package ru.kvshe.languagetrainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    /**
     * Возвращает список из случайных слов в количестве quantity
     *
     * @return
     */
    public List<Word> getRandomWords() {
        int quantity = 10;
        return wordRepository.findRandomWords(quantity);
    }

    public Optional<Word> getById(Long id) {
        return wordRepository.findById(id);
    }

    public Word save(Word word) {
        word.setLastUsed(LocalDate.now());
        return wordRepository.save(word);
    }

    public Word update(Long id, Word word) {
        word.setId(id);
        return wordRepository.save(word);
    }

    public void deleteById(Long id) {
        wordRepository.deleteById(id);
    }

    public Word updateLastUsed(Word word) {
        word.setLastUsed(LocalDate.now());
        return update(word.getId(), word);
    }

    public List<Word> sortByEnglish(List<Word> words) {
        words.sort(Comparator.comparing(Word::getEnglish));
        return words;
    }

    public List<Word> sortByRussian(List<Word> words) {
        words.sort(Comparator.comparing(Word::getRussian));
        return words;
    }
}
