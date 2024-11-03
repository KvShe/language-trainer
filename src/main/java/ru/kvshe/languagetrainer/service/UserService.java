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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Метод получает login текущего пользователя из SecurityContextHolder
     *
     * @return login текущего пользователя
     */
    public String getLoginCurrentUser() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    public User getUserByLogin(String login) {
        return userRepository.findAllByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found"));
    }

    public User getCurrentUser() {
        return getUserByLogin(getLoginCurrentUser());
    }

    public User save(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
