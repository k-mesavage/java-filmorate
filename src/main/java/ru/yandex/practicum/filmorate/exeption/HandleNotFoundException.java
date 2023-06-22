package ru.yandex.practicum.filmorate.exeption;

public class HandleNotFoundException extends RuntimeException {
    public HandleNotFoundException(String message) {
        super(message);
    }
}
