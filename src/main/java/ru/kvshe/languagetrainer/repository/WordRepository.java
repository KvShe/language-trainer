package ru.kvshe.languagetrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kvshe.languagetrainer.model.Word;

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
}
