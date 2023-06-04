package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.manager.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static final FilmManager filmManager = new FilmManager();

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        return filmManager.getAllFilms();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidException {
        if (filmManager.updateFilm(film)) {
            log.info("Добавлен фильм id: ");
        } else {
            log.info("Ошибка обновления фильма id: " + film.getId() + ", фильм не найден");
            throw new ValidException("InvalidFilmUpdate");
        }
        return film;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
            filmManager.addFilm(film);
            log.info("Добавление фильма id: " + film.getId());
        return film;
    }
}
