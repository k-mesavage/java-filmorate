package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFilmManager implements FimManager {

    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private Integer id = 0;

    private void generateId(Film film) {
        ++id;
        film.setId(id);
    }

    private List<Film> getAll() {
        return new ArrayList<>(filmsMap.values());
    }

    private Film getFilm(int id) {
        return filmsMap.get(id);
    }

    private Film addFilm(int id, Film film) {
        filmsMap.put(id, film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return getAll();
    }

    @Override
    public Film addFilm(Film film) {
        generateId(film);
        addFilm(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (getFilm(film.getId()) != null) {
            addFilm(film.getId(), film);
            return film;
        }
        else {
            throw new ValidException("InvalidFilmUpdate");
        }
    }
}