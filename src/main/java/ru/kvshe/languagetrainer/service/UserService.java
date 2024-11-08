package ru.kvshe.languagetrainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.Role;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.repository.UserRepository;

import java.util.List;

/**
 * Сервис для управления пользователями. Позволяет регистрировать пользователей, получать данные о текущем пользователе и взаимодействовать с репозиторием пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Получает логин текущего пользователя из контекста безопасности.
     *
     * @return логин текущего пользователя
     */
    public String getLoginCurrentUser() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    /**
     * Получает пользователя по его логину.
     *
     * @param login логин пользователя
     * @return объект пользователя
     * @throws UsernameNotFoundException если пользователь с данным логином не найден
     */
    public User getUserByLogin(String login) {
        return userRepository.findAllByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found"));
    }

    /**
     * Получает текущего пользователя.
     *
     * @return объект текущего пользователя
     */
    public User getCurrentUser() {
        return getUserByLogin(getLoginCurrentUser());
    }

    /**
     * Регистрирует нового пользователя в системе. Устанавливает роль пользователя как USER и хеширует пароль.
     *
     * @param user объект пользователя, который должен быть сохранён
     * @return сохранённый объект пользователя
     */
    public User save(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Получает список всех пользователей в системе.
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
