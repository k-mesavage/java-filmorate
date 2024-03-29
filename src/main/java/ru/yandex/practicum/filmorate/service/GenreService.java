package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.GenreManager;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {

    private final GenreManager manager;

    public List<Genre> getAllGenres() {
        return manager.getAllGenres();
    }

    public Genre getGenreById(int id) {
        return manager.getGenreById(id);
    }
}
