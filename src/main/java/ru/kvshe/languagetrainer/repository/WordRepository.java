package ru.kvshe.languagetrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kvshe.languagetrainer.model.Word;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с сущностью `Word`.
 * Содержит методы для поиска, обновления и подсчета слов пользователя.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    /**
     * Возвращает список случайных слов в количестве quantity для user с id - userId
     *
     * @param userId   идентификатор пользователя
     * @param quantity количество, записей, которые вернутся из базы данных
     * @return список случайных слов
     */
    @Query(value = "select * from word where user_id = :userId order by random() limit :quantity", nativeQuery = true)
    List<Word> findRandomWordsForUserId(@Param("userId") Long userId, @Param("quantity") int quantity);

    /**
     * Возвращает список слов, которые не использовались более заданного времени
     *
     * @param userId   идентификатор пользователя
     * @param quantity количество записей, которые метод вернёт
     * @param days     число дней, если поле las_used отличается от текущей даты на большее количество, то эта запись будет возвращена
     * @return список слов, которые не проверялись более days
     */
    @Query(value = "select * from word where user_id = :userId and last_used < now() - cast(:days || ' days' as interval) order by random() limit :quantity", nativeQuery = true)
    List<Word> findRandomWordsNotUsedInLastDaysForUser(@Param("userId") Long userId, @Param("quantity") int quantity, @Param("days") int days);

    /**
     * Метод количество слов, которые не использовались более days для user с id - userId
     *
     * @param userId идентификатор пользователя
     * @param days   число дней, если поле las_used отличается от текущей даты на большее количество, то эта запись будет возвращена
     * @return количество слов, которые не использовались более указанного количества дней
     */
    @Query(value = "select count(*) from word where user_id = :userId and last_used < now() - cast(:days || ' days' as interval)", nativeQuery = true)
    int countWordsNotUsedInLastDaysForUser(@Param("userId") Long userId, @Param("days") int days);

    /**
     * Обновляет у записей поле lastUsed
     *
     * @param dateUse дата, на которую будет обновлено поле lastUsed
     * @param ids     идентификаторы записей, у которых будет обновлено поле lastUsed
     */
    @Modifying
    @Query("update Word w set w.lastUsed = :dateUse where w.id in :ids")
    void updateWordsTextByIds(@Param("dateUse") LocalDate dateUse, @Param("ids") List<Long> ids);

    /**
     * Ищет все слова по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список всех слов, связанных с пользователем
     */
    List<Word> findAllByUserId(Long userId);
}
