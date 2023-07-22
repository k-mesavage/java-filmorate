package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService manager;

    @GetMapping
    public List<Genre> getGenres() {
        val answer = manager.getAllGenres();
        log.info("Get Genres {}", answer);
        return answer;
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        val answer = manager.getGenreById(id);
        log.info("Get Genre By Id {}", answer);
        return answer;
    }
}
