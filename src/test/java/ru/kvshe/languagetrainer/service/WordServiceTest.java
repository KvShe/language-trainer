package ru.kvshe.languagetrainer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WordServiceTest {

    @Mock
    private WordRepository wordRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private WordService wordService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
    }

    @Test
    void testGetAll() {
        List<Word> words = List.of(new Word(), new Word());
        when(wordRepository.findAll()).thenReturn(words);

        List<Word> result = wordService.getAll();

        assertEquals(words.size(), result.size());
        verify(wordRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFor() {
        Long userId = 1L;
        List<Word> words = List.of(new Word(), new Word());
        when(wordRepository.findAllByUserId(userId)).thenReturn(words);

        List<Word> result = wordService.getAllFor(userId);

        assertEquals(words.size(), result.size());
        verify(wordRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void testGetRandomWords() {
        int quantity = 10;
        List<Word> randomWords = List.of(new Word(), new Word());
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(wordRepository.findRandomWordsForUserId(mockUser.getId(), quantity)).thenReturn(randomWords);

        List<Word> result = wordService.getRandomWords();

        assertEquals(randomWords.size(), result.size());
        verify(wordRepository, times(1)).findRandomWordsForUserId(mockUser.getId(), quantity);
    }

    @Test
    void testSave() {
        Word word = new Word();
        word.setUserId(mockUser.getId());
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(wordRepository.save(word)).thenReturn(word);

        Word savedWord = wordService.save(word);

        assertNotNull(savedWord);
        assertEquals(mockUser.getId(), savedWord.getUserId());
        assertEquals(LocalDate.now(), savedWord.getLastUsed());
        verify(wordRepository, times(1)).save(word);
    }

    @Test
    void testUpdate() {
        Word word = new Word();
        word.setId(1L);
        when(wordRepository.save(word)).thenReturn(word);

        Word updatedWord = wordService.update(1L, word);

        assertEquals(1L, updatedWord.getId());
        verify(wordRepository, times(1)).save(word);
    }

    @Test
    void testDeleteById() {
        Long wordId = 1L;

        wordService.deleteById(wordId);

        verify(wordRepository, times(1)).deleteById(wordId);
    }

    @Test
    void testUpdateWordsData() {
        List<Word> words = List.of(new Word(), new Word());
        words.get(0).setId(1L);
        words.get(1).setId(2L);
        List<Long> ids = List.of(1L, 2L);

        wordService.updateWordsData(words);

        verify(wordRepository, times(1)).updateWordsTextByIds(LocalDate.now(), ids);
    }

    @Test
    void testGetCountForgottenWords() {
        int days = 2;
        int count = 5;
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(wordRepository.countWordsNotUsedInLastDaysForUser(mockUser.getId(), days)).thenReturn(count);

        int result = wordService.getCountForgottenWords();

        assertEquals(count, result);
        verify(wordRepository, times(1)).countWordsNotUsedInLastDaysForUser(mockUser.getId(), days);
    }

    @Test
    void testGetForgottenWords() {
        int quantity = 10;
        int days = 2;
        List<Word> forgottenWords = List.of(new Word(), new Word());
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(wordRepository.findRandomWordsNotUsedInLastDaysForUser(mockUser.getId(), quantity, days)).thenReturn(forgottenWords);

        List<Word> result = wordService.getForgottenWords();

        assertEquals(forgottenWords.size(), result.size());
        verify(wordRepository, times(1)).findRandomWordsNotUsedInLastDaysForUser(mockUser.getId(), quantity, days);
    }
}
