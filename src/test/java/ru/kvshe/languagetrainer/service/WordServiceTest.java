package ru.kvshe.languagetrainer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class WordServiceTest {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordService wordService;

    @BeforeEach
    void setUp() {
        Word wordA = new Word();
        Word wordB = new Word();

        wordA.setId(1L);
        wordA.setEnglish("expected");
        wordA.setRussian("ожидаемое");

        wordB.setId(2L);
        wordB.setEnglish("extend");
        wordB.setRussian("продлевать");

        List<Word> expected = List.of(wordA, wordB);
        wordRepository.saveAll(expected);
    }

    @Test
    void getAll() {
        // pre
        List<Word> expected = wordRepository.findAll();

        // action
        List<Word> actual = wordService.getAll();

        // check
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getEnglish(), actual.get(i).getEnglish());
            assertEquals(expected.get(i).getRussian(), actual.get(i).getRussian());
        }
    }

    @Test
    void getById() {
        // pre
        Word expected = wordRepository.findById(1L).orElse(null);
        assertNotNull(expected);

        // action
        Word actual = wordService.getById(expected.getId())
                .orElse(null);

        // check
        assertNotNull(actual);
        assertEquals(expected.getEnglish(), actual.getEnglish());
        assertEquals(expected.getRussian(), actual.getRussian());
    }

    @Test
    void save() {
        // pre
        Word expected = new Word();
        expected.setId(3L);
        expected.setEnglish("expected");
        expected.setRussian("ожидаемое");

        // action
        Word actual = wordService.save(expected);

        // check
        assertEquals(expected.getEnglish(), actual.getEnglish());
        assertEquals(expected.getRussian(), actual.getRussian());

        assertNotNull(actual.getLastUsed());
        assertTrue(wordRepository.existsById(actual.getId()));
    }

    @Test
    void update() {
        // pre
        Word expected = new Word();
        expected.setId(2L);
        expected.setEnglish("expected");
        expected.setRussian("ожидаемое");

        // action
        Word actual = wordService.save(expected);

        // check
        assertNotNull(actual);

        assertEquals(expected.getEnglish(), actual.getEnglish());
        assertEquals(expected.getRussian(), actual.getRussian());

        assertNotNull(actual.getLastUsed());
    }

    @Test
    void deleteById() {
        // action
        wordService.deleteById(1L);

        // check
        assertFalse(wordRepository.existsById(1L));
    }
}
