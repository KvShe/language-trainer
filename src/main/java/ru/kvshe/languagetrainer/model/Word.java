package ru.kvshe.languagetrainer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String english;
    private String russian;
    @Column(name = "last_used")
    private LocalDate lastUsed;

    @Override
    public String toString() {
        return english + " - " + russian;
    }
}
