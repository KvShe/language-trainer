package ru.kvshe.languagetrainer.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kvshe.languagetrainer.model.User;
import ru.kvshe.languagetrainer.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "API для управления пользователями")
public class AuthRestController {
    private final UserService userService;

    @GetMapping("/users")
    @Operation(
            summary = "Получить список пользователей",
            description = "Возвращает список всех зарегистрированных пользователей."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список пользователей успешно получен",
            content = @Content(schema = @Schema(implementation = User.class))
    )
    public List<User> showUsers() {
        return userService.getAllUsers();
    }
}
