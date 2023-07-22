package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmManager {

    List<Film> getAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void generateId(Film film);

    Film getFilmById(int id);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);

    List<Film> getMostLiked(String countParam);

    int getMaxId();
}