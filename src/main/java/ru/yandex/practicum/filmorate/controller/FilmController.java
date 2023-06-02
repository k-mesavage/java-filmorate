package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    Integer id = 0;
    private HashMap<Integer, Film> filmsMap = new HashMap<>();
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 11,28);
    private void idGenerator(Film film) {
        film.setId(++id);
    }

    @GetMapping
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(filmsMap.values());
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidException{
        if(filmsMap.get(film.getId()) != null && film.getReleaseDate().isAfter(MIN_RELEASE_DATE)) {
            filmsMap.put(film.getId(), film);
        } else {
            throw new ValidException("InvalidFilmUpdate");
        }
        return film;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidException {
        if(film.getReleaseDate().isAfter(MIN_RELEASE_DATE)) {
            idGenerator(film);
            filmsMap.put(film.getId(), film);
        } else {
            throw new ValidException("InvalidReleaseDate");
        }
        return film;
    }
}
