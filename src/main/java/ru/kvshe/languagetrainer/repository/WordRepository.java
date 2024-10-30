package ru.kvshe.languagetrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kvshe.languagetrainer.model.Word;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    /**
     * Возвращает список случайных слов в количестве quantity
     *
     * @return список случайных слов
     */
    @Query(value = "select * from word order by random() limit :quantity", nativeQuery = true)
    List<Word> findRandomWords(@Param("quantity") int quantity);

//    @Query(value = "select * from word where last_used < now() - interval '5 days' order by random() limit :quantity", nativeQuery = true)
//    List<Word> findRandomWordsNotUsedInLastFiveDays(@Param("quantity") int quantity);

    /**
     * Возвращает список слов, которые не использовались более заданного времени
     * @param quantity количество записей, которые метод вернёт
     * @param days число дней, если поле las_used отличается от текущей даты на большее количество, то эта запись будет возвращена
     * @return список слов, которые не проверялись более days
     */
    @Query(value = "select * from word where last_used < now() - cast(:days || ' days' as interval) order by random() limit :quantity", nativeQuery = true)
    List<Word> findRandomWordsNotUsedInLastDays(@Param("quantity") int quantity, @Param("days") int days);

//    @Modifying
//    @Query("update Word set Word.lastUsed = :data")
//    void updateAllWordsData(@Param("data") LocalDate data);

    /**
     * Обновляет у записей поле lastUsed
     * @param dateUse дата, на которую будет обновлено поле lastUsed
     * @param ids идентификаторы записей, у которых будет обновлено поле lastUsed
     */
    @Modifying
    @Query("update Word w set w.lastUsed = :dateUse where w.id in :ids")
    void updateWordsTextByIds(@Param("dateUse") LocalDate dateUse, @Param("ids") List<Long> ids);
}
