package ru.kvshe.languagetrainer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление ролей пользователей в системе.
 * Каждая роль определяет уровень доступа пользователя.
 */
@Getter
@AllArgsConstructor
public enum Role {
    USER("user"), ADMIN("admin");
    private final String name;
}
