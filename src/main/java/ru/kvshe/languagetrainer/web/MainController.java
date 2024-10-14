package ru.kvshe.languagetrainer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kvshe.languagetrainer.util.DataServer;

@Controller
public class MainController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("ip", DataServer.getIp());
        return "index";
    }
}
