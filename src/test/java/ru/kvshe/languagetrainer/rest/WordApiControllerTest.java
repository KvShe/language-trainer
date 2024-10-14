package ru.kvshe.languagetrainer.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WordApiControllerTest {
    @LocalServerPort
    private int port;
    private RestClient restClient;

    @Autowired
    private WordRepository wordRepository;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + port + "/api/words");
    }

    @Test
    void testGetAllWords() {
        // given
        Word wordA = new Word();
        Word wordB = new Word();

        wordA.setEnglish("execute");
        wordA.setRussian("выполнять");

        wordB.setEnglish("expected");
        wordB.setRussian("ожидаемое");

        List<Word> expected = List.of(wordA, wordB);

        wordRepository.save(wordA);
        wordRepository.save(wordB);

        // when
        ResponseEntity<List<Word>> entity = restClient.get()
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

        // then
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<Word> actual = entity.getBody();

        assertNotNull(actual);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getEnglish(), actual.get(i).getEnglish());
            assertEquals(expected.get(i).getRussian(), actual.get(i).getRussian());
        }
    }

    @Test
    void testGetWordById() {
        // given
        Word expected = new Word();
        expected.setEnglish("expected");
        expected.setRussian("ожидаемое");

        wordRepository.save(expected);

        // when
        ResponseEntity<Word> response = restClient.get()
                .uri("/{id}", expected.getId())
                .retrieve()
                .toEntity(Word.class);

        // then
        Word actual = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(actual);
        assertEquals(expected.getEnglish(), actual.getEnglish());
        assertEquals(expected.getRussian(), actual.getRussian());
    }

    @Test
    void testCreateWord() {
        // given
        Word toCreate = new Word();
        toCreate.setId(1L);
        toCreate.setEnglish("expected");
        toCreate.setRussian("ожидаемое");

        // when
        ResponseEntity<Word> response = restClient.post()
                .uri("")
                .body(toCreate)
                .retrieve()
                .toEntity(Word.class);

        // then
        Word actual = wordRepository.findById(toCreate.getId()).orElse(null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(actual);
        assertEquals(toCreate.getEnglish(), actual.getEnglish());
        assertEquals(toCreate.getRussian(), actual.getRussian());
    }

    @Test
    void testUpdateWord() {
        // given
        Word word = new Word();
        word.setId(1L);
        word.setEnglish("expected");
        word.setRussian("ожидаемое");
        wordRepository.save(word);

        Word toUpdate = new Word();
        toUpdate.setEnglish("update");
        toUpdate.setRussian("обновлять");

        // when
        ResponseEntity<Word> response = restClient.put()
                .uri("/{id}", word.getId())
                .body(toUpdate)
                .retrieve()
                .toEntity(Word.class);

        // then
        Word actual = wordRepository.findById(word.getId()).orElse(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(actual);
        assertEquals(toUpdate.getEnglish(), actual.getEnglish());
        assertEquals(toUpdate.getRussian(), actual.getRussian());

        assertTrue(wordRepository.existsById(word.getId()));
    }

    @Test
    void testDeleteWord() {
        // given
        Word toDelete = new Word();
        toDelete.setId(1L);
        toDelete.setEnglish("expected");
        toDelete.setRussian("ожидаемое");

        wordRepository.save(toDelete);

        // when
        ResponseEntity<Void> response = restClient.delete()
                .uri("/{id}", toDelete.getId())
                .retrieve()
                .toBodilessEntity();

        // when
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(wordRepository.existsById(toDelete.getId()));
    }
}
