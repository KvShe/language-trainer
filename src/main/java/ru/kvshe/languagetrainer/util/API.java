package ru.kvshe.languagetrainer.util;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.kvshe.languagetrainer.exception.NotFoundWordException;
import ru.kvshe.languagetrainer.model.Word;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public final class API {
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = List.class)))
    public @interface OkList {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Word.class)))
    public @interface OkWord {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Пользователи не найдены",
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = NotFoundWordException.class)))
    public @interface NotFoundResponse {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(
            description = "Внутренняя ошибка",
            responseCode = "500",
            content = @Content(schema = @Schema())
    )
    public @interface InternalServerError {
    }
}
