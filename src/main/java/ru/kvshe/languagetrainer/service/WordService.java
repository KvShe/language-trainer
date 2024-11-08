package ru.kvshe.languagetrainer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.model.Word;
import ru.kvshe.languagetrainer.repository.WordRepository;
import ru.kvshe.languagetrainer.util.sort.SortStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final UserService userService;

    private SortStrategy sortStrategy;
    private int quantity = 10; // количество слов в одном уроке
    private int days = 2;

    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    public List<Word> getAllFor(Long userId) {
        return wordRepository.findAllByUserId(userId);
    }

    public void sortWords(List<Word> words) {
        sortStrategy.sort(words);
    }

    /**
     * Метод получает текущего user.
     * Возвращает список из случайных слов в количестве quantity для текущего user
     *
     * @return список из случайных слов для текущего пользователя
     */
    public List<Word> getRandomWords() {
        Long userId = userService.getCurrentUser().getId();
        return wordRepository.findRandomWordsForUserId(userId, quantity);
    }

    public Optional<Word> getById(Long id) {
        return wordRepository.findById(id);
    }

    /**
     * Метод получает текущего пользователя.
     * Назначает word в поле userId - id текущего пользователя, и в поле lastUsed - текущую дату.
     * Сохраняет word в базу данных
     *
     * @param word слово, которое user передал через клиента
     * @return word, который сохранён в базе данных
     */
    public Word save(Word word) {
        User currentUser = userService.getCurrentUser();

        word.setUserId(currentUser.getId());
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

    /**
     * Получает из списка слов идентификаторы и передаёт их в метод, для обновления поля lastUsed на текущую дату
     *
     * @param words список слов, у которых требуется обновить поле lastUsed
     */
    @Transactional
    public void updateWordsData(List<Word> words) {
        List<Long> ids = words.stream()
                .map(Word::getId)
                .toList();

        wordRepository.updateWordsTextByIds(LocalDate.now(), ids);
    }

    public int getCountForgottenWords() {
        Long id = userService.getCurrentUser().getId();

        return wordRepository.countWordsNotUsedInLastDaysForUser(id, days);
    }

    /**
     * Метод возвращает список слов, которые текущим пользователем не использовались более указанного времени (days)
     * @return список слов, которые пользователем с id не использовались в течении days
     */
    public List<Word> getForgottenWords() {
        Long id = userService.getCurrentUser().getId();

        return wordRepository.findRandomWordsNotUsedInLastDaysForUser(id, quantity, days);
    }
}
