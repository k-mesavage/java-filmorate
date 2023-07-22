package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.GenreManager;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Service
public class GenreService {
    GenreManager manager;

    @Autowired
    public GenreService(GenreManager genreManager) {
        this.manager = genreManager;
    }

    public List<Genre> getAllGenres() {
        return manager.getAllGenres();
    }

    public Genre getGenreById(int id) {
        return manager.getGenreById(id);
    }
}
