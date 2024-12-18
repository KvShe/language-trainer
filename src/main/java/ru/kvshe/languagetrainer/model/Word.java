package ru.kvshe.languagetrainer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
/**
 * Класс, представляющий сущность "Слово" для изучения в приложении.
 * Каждое слово имеет английский и русский переводы, дату последнего использования и привязку к пользователю.
 */
@Entity
@Getter
@Setter
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String english;
    private String russian;
    @Column(name = "last_used")
    private LocalDate lastUsed;

    @Override
    public String toString() {
        return english + " - " + russian;
    }
}
