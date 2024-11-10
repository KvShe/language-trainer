package ru.kvshe.languagetrainer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий сущность "Пользователь" в системе Language Trainer.
 * Пользователь имеет уникальный логин, пароль и роль, определяющую его уровень доступа.
 */
@Entity
@Table(name = "user_lt", uniqueConstraints = @UniqueConstraint(columnNames = "login")) // уникальность для login
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String toString() {
        return id + " " + login + " " + password + " " + role;
    }
}
