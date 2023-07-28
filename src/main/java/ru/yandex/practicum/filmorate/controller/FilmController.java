package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService manager;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Add Film id: {}", film.getId());
        return manager.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Update Film id: {}", film.getId());
        return manager.updateFilm(film);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return manager.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Get Film id:{}", id);
        return manager.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Add Like Film {}", id);
        return manager.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Delete Like Film {}", id);
        return manager.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostLiked(@RequestParam(required = false, defaultValue = "10") String count) {
        log.info("Get Most Liked {} Films", count);
        return manager.getMostLiked(count);
    }
}
