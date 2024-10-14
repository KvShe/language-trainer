package ru.kvshe.languagetrainer.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kvshe.languagetrainer.exception.NotFoundWordException;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.service.WordService;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordApiController {
    private final WordService wordService;

    @GetMapping
    public ResponseEntity<List<Word>> getAllWords() {
        return ResponseEntity.ok(wordService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Word> getWordById(@PathVariable Long id) {
//        Word word = wordService.getById(id).orElse(null);

        return ResponseEntity.ok(
                wordService.getById(id)
                        .orElseThrow(() -> new NotFoundWordException(id)));
    }

    @PostMapping
    public ResponseEntity<Word> createWord(@RequestBody Word word) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(wordService.save(word));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Word> updateWord(@PathVariable Long id, @RequestBody Word word) {
        return ResponseEntity.ok(wordService.update(id, word));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        wordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
