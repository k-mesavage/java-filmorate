package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.storage.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    @Qualifier("FilmDbManager")
    private FilmManager films;

    public Film addFilm(Film film) {
         return films.addFilm(film);
    }

    public Film getFilm(int id) {
        return films.getFilmById(id);
    }

    public Film updateFilm(Film film) {
        if (films.getFilmById(film.getId()) != null) {
            return films.updateFilm(film);
        } else throw new NotFoundException("Film Not Found");
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.getAllFilms());
    }

    public Film addLike(int id, int userId) {
        films.addLike(id, userId);
        return films.getFilmById(id);
    }

    public Film deleteLike(int id, int userId) {
        films.deleteLike(id, userId);
        return films.getFilmById(id);
    }

    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        if (count < 0) {
            throw new ValidationException("MostLikedException");
        }
        return films.getMostLiked(countParam);
    }
}
