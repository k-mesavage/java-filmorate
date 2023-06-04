package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public abstract class Manager {
    ArrayList<User> getAllUsers() {
        return null;
    }

    ArrayList<Film> getAllFilms() {
        return null;
    }

    void addUser(User user) {
    }

    void addFilm(Film film) {
    }

    Boolean updateUser(User user) {
        return null;
    }

    Boolean updateFilm(Film film) {
        return null;
    }
}
