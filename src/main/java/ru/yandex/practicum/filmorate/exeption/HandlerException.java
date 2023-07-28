package ru.yandex.practicum.filmorate.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MpaController;
import ru.yandex.practicum.filmorate.controller.UserController;

import java.util.Map;
import java.util.Set;

@RestControllerAdvice(assignableTypes
        = {FilmController.class, UserController.class, GenreController.class, MpaController.class})
public class HandlerException {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidationException(final ValidationException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Set<String> handleNotFoundException(final NotFoundException e) {
        return Set.of(e.getMessage());
    }
}
