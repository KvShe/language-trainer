package ru.kvshe.languagetrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kvshe.languagetrainer.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
