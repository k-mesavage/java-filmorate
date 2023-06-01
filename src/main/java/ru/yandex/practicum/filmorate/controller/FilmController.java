package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();
    Integer id = 0;
    private static LocalDate MIN_REALISE_DATE = LocalDate.of(1895, 11, 28);

    private void idGenerator(Film film) {
        ++id;
        film.setId(id);
    }

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isAfter(MIN_REALISE_DATE)) {
            idGenerator(film);
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("releaseDate");
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
            if (film.getReleaseDate().isAfter(MIN_REALISE_DATE) || films.get(film.getId()) != null) {
                films.put(film.getId(), film);
            } else {
                throw new ValidationException("releaseDate");
            }
        return film;
    }
}
