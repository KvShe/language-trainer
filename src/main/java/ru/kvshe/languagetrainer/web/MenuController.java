package ru.kvshe.languagetrainer.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kvshe.languagetrainer.service.UserService;
import ru.kvshe.languagetrainer.service.WordService;
import ru.kvshe.languagetrainer.util.DataServer;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final WordService wordService;
    private final UserService userService;
    private final ConfigurableApplicationContext ctx;

    @GetMapping
    public String index(Model model) {
        String loginCurrentUser = userService.getLoginCurrentUser();

        if (loginCurrentUser.equals("anonymousUser")) {
            model.addAttribute("count", 0);
        } else {
            int count = wordService.getCountForgottenWords();
            model.addAttribute("count",
                    count > 10 ? "10+" : count);
        }

        model.addAttribute("ip", DataServer.getIp());
        return "index";
    }

    // todo реализовать кнопку (или ссылку) для закрытия программы
    @PostMapping("/exit")
    public void exit() {
        ctx.close();
    }
}
