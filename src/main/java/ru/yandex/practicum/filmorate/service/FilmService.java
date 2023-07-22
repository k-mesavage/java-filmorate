package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.manager.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    FilmManager films;
    FilmValidator validator;

    @Autowired
    private FilmService(@Qualifier("FilmDbManager") FilmManager filmManager, FilmValidator validator) {
        this.films = filmManager;
        this.validator = validator;
    }

    public Film addFilm(Film film) {
        FilmValidator.validate(film);
        films.addFilm(film);
        return films.getFilmById(film.getId());
    }

    public Film getFilm(int id) {
        return films.getFilmById(id);
    }

    public Film updateFilm(Film film) {
        if (films.getFilmById(film.getId()) != null) {
            FilmValidator.validate(film);
            films.updateFilm(film);
            return film;
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
