package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreManager {

    List<Genre> getAllGenres();

    Genre getGenreById(int id);
}
