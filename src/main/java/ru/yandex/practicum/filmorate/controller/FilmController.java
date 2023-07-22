package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.HandleNotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService manager;

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получены все фильмы");
        return manager.getAllFilms();
    }

    @GetMapping("/popular")
    public Set<Film> getPopularFilm(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        log.info("Получены популярные фильмы");
        return manager.getPopularFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilmByIds(@PathVariable int id) {
        log.info("Получен фильм {}", id);
        return manager.getFilmById(id);
    }

    @PutMapping
    public Film updateFilms(@Valid @RequestBody Film film) throws ValidException, HandleNotFoundException {
        if (manager.updateFilm(film) == film) {
            log.info("Добавлен фильм {}", film);
        } else {
            log.info("Ошибка обновления фильма {}", film);
        }
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikes(@PathVariable int id, @PathVariable int userId) throws HandleNotFoundException {
        log.info("Лайк фильму {}", id);
        manager.addLike(id, userId);
    }

    @PostMapping
    public Film addFilms(@Valid @RequestBody Film film) {
        manager.addFilm(film);
        log.info("Добавление фильма {}", film);
        return film;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikes(@PathVariable int id, @PathVariable int userId) throws HandleNotFoundException {
        log.info("Удален лайк фильму {}", id);
        manager.deleteLike(id, userId);
    }
}
