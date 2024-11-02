package ru.kvshe.languagetrainer.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> showUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping("/current-user")
//    public String getCurrentUser() {
//        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return principal.getName(); // возвращает имя пользователя
//    }
}
