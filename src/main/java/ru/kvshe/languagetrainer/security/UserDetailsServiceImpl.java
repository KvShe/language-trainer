package ru.kvshe.languagetrainer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.service.UserService;

import java.util.List;

/**
 * Реализация сервиса для получения данных о пользователе из базы данных для Spring Security.
 * Этот сервис используется для аутентификации пользователей и назначения ролей.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    /**
     * Метод для загрузки данных пользователя по его логину.
     * Загружает пользователя из базы данных через UserService и преобразует его в объект UserDetails,
     * который используется для аутентификации и авторизации в приложении.
     *
     * @param login логин пользователя, который требуется аутентифицировать
     * @return объект UserDetails, содержащий данные пользователя и его роль
     * @throws UsernameNotFoundException если пользователь с указанным логином не найден
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getName()))
        );
    }
}
