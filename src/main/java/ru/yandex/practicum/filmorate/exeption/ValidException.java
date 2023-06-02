package ru.yandex.practicum.filmorate.exeption;

import java.io.IOException;

public class ValidException extends IOException {
    public ValidException(String message) {
        super(message);
    }
}
