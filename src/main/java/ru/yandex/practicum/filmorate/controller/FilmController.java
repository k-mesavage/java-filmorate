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
import ru.yandex.practicum.filmorate.manager.InMemoryFilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController extends InMemoryFilmManager {

    private static final InMemoryFilmManager manager = new InMemoryFilmManager();

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public List<Film> getAllFilms() {
        return manager.getAllFilms();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidException {
        if (manager.updateFilm(film) == film) {
            log.info("Добавлен фильм id: ");
        } else {
            log.info("Ошибка обновления фильма id: " + film.getId() + ", фильм не найден");
        }
        return film;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
            manager.addFilm(film);
            log.info("Добавление фильма id: " + film.getId());
            return film;
    }
}
