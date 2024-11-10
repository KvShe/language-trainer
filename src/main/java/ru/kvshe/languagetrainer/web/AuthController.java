package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.service.UserService;

@Controller
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Контроллер для аутентификации и регистрации пользователей")
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    @Operation(summary = "Показать форму регистрации", description = "Возвращает форму для регистрации нового пользователя.")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("auth/register")
                .addObject("user", new User());
    }

    @PostMapping("/register")
    @Operation(summary = "Создать нового пользователя", description = "Сохраняет нового пользователя и перенаправляет на страницу входа.")
    public ModelAndView createNewUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.save(user);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    @Operation(summary = "Показать форму входа", description = "Возвращает форму для входа пользователя.")
    public ModelAndView showLoginForm() {
        return new ModelAndView("auth/login");
    }

    @PostMapping("/login")
    @Operation(summary = "Вход пользователя", description = "Обрабатывает данные входа пользователя.")
    public ModelAndView login(@ModelAttribute("user") User user) {
        return new ModelAndView("auth/login");
    }
}
