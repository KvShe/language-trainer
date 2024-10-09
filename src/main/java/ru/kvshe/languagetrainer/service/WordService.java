package ru.kvshe.languagetrainer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.exception.NotFoundWordException;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository repository;

    public List<Word> getAll() {
        return repository.findAll();
    }

    public Word getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundWordException(id));
    }

    public void save(Word word) {
        word.setLastUsed(LocalDate.now());
        repository.save(word);
    }

    public void update(Long id, Word word) {
        word.setId(id);
        repository.save(word);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
