package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kvshe.languagetrainer.service.UserService;
import ru.kvshe.languagetrainer.service.WordService;
import ru.kvshe.languagetrainer.util.DataServer;

@Controller
@RequiredArgsConstructor
@Tag(name = "Menu Controller", description = "Управление главной страницей и выходом из приложения")
public class MenuController {
    private final WordService wordService;
    private final UserService userService;

    @GetMapping("/")
    @Operation(
            summary = "Показать главную страницу",
            description = "Возвращает индексную страницу с количеством забытых слов для текущего пользователя и IP-адресом сервера."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Главная страница успешно загружена",
            content = @Content(schema = @Schema(type = "string", format = "html"))
    )
    public String index(Model model) {
        String loginCurrentUser = userService.getLoginCurrentUser();

        if (loginCurrentUser.equals("anonymousUser")) {
            model.addAttribute("count", 0);
        } else {
            int count = wordService.getCountForgottenWords();
            model.addAttribute("count", count > 10 ? "10+" : count);
        }

        model.addAttribute("ip", DataServer.getIp());
        return "index";
    }
}
