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
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private Integer id = 0;
    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private void generateId(Film film) {
        film.setId(++id);
    }
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);


    private ArrayList<Film> getFilms(){
        return new ArrayList<>(filmsMap.values());
    }

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        return getFilms();
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidException {
        if (filmsMap.get(film.getId()) != null) {
            filmsMap.put(film.getId(), film);
            log.info("Обновление фильма id: " + film.getId());
        } else {
            log.info("Ошибка обновления фильма id: " + film.getId() + ", фильм не найден");
            throw new ValidException("InvalidFilmUpdate");
        }
        return film;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
            generateId(film);
            filmsMap.put(film.getId(), film);
            log.info("Добавление фильма id: " + film.getId());
        return film;
    }
}
