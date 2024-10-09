package ru.kvshe.languagetrainer.exception;

/**
 * Исключение, которое выбрасывается в случае, если объект класса Word с id - wordId не найден в базе данных
 */
public class NotFoundWordException extends RuntimeException {
    public NotFoundWordException(Long wordId) {
        super("Word with id " + wordId + " not found");
    }
}
