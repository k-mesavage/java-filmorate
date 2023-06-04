package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilmManager extends Manager {

    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private Integer id = 0;

    private void generateId(Film film) {
        ++id;
        film.setId(id);
    }

    private ArrayList<Film> getAll() {
        return new ArrayList<>(filmsMap.values());
    }

    private Film getFilm(int id) {
        return filmsMap.get(id);
    }

    private void addFilm(int id, Film film) {
        filmsMap.put(id, film);
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return getAll();
    }

    @Override
    public void addFilm(Film film) {
        generateId(film);
        addFilm(film.getId(), film);
    }

    @Override
    public Boolean updateFilm(Film film) {
        if (getFilm(film.getId()) != null) {
            addFilm(film.getId(), film);
            return true;
        }
        return false;
    }
}