package ru.kvshe.languagetrainer.web;

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
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("auth/register")
                .addObject("user", new User());
    }

    @PostMapping("/register")
    public ModelAndView createNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/");
    }
}
