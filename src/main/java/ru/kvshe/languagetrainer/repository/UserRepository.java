package ru.kvshe.languagetrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kvshe.languagetrainer.model.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью `User`.
 * Содержит методы для поиска пользователей по их логину.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Ищет пользователя по его логину.
     *
     * @param login логин пользователя
     * @return объект `Optional<User>`, который может содержать пользователя с заданным логином или быть пустым,
     * если пользователь не найден
     */
    Optional<User> findAllByLogin(String login);
}
