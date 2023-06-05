package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FimManager {

    List<Film> getAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);
}