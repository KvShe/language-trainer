package ru.kvshe.languagetrainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository repository;

    public List<Word> getAll() {
        return repository.findAll();
    }

    public Optional<Word> getById(Long id) {
        return repository.findById(id);
    }

    public Word save(Word word) {
        word.setLastUsed(LocalDate.now());
        return repository.save(word);
    }

    public Word update(Long id, Word word) {
        word.setId(id);
        return repository.save(word);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Word updateLastUsed(Word word) {
        word.setLastUsed(LocalDate.now());
        return update(word.getId(), word);
    }
}
