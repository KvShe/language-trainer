package ru.kvshe.languagetrainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void updateAll(List<Word> words) {
        wordRepository.saveAll(words);
    }

    /**
     * Получает из списка слов идентификаторы и передаёт их в метод, для обновления поля lastUsed на текущую дату
     * @param words список слов, у которых требуется обновить поле lastUsed
     */
    @Transactional
    public void updateWordsData(List<Word> words) {
        List<Long> ids = words.stream()
                .map(Word::getId)
                .toList();

        wordRepository.updateWordsTextByIds(LocalDate.now(), ids);
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
