package ru.kvshe.languagetrainer.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kvshe.languagetrainer.util.DataServer;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final ConfigurableApplicationContext ctx;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ip", DataServer.getIp());
        return "index";
    }

    // todo реализовать кнопку (или ссылку) для закрытия программы
    @PostMapping("/exit")
    public void exit() {
        ctx.close();
    }
}
