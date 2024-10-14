package ru.kvshe.languagetrainer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается в случае, если объект класса Word с id - wordId не найден в базе данных
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundWordException extends RuntimeException {
    public NotFoundWordException(Long wordId) {
        super("Word with id " + wordId + " not found");
    }
}
