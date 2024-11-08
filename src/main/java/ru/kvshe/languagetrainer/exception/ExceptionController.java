package ru.kvshe.languagetrainer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Глобальный обработчик исключений для приложения Language Trainer.
 * Позволяет обрабатывать исключения и перенаправлять пользователя на соответствующие страницы ошибок.
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundWordException.class)
    public ModelAndView exceptionHandler() {
        return new ModelAndView("exceptions/oops", HttpStatus.BAD_REQUEST);
//                .addObject("ex", ex.getMessage());
    }
}
