package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.HandleNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private Integer id = 0;

    private void generateId(Film film) {
        ++id;
        film.setId(id);
    }

    public Film addFilm(Film film) {
        generateId(film);
        filmsMap.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (filmsMap.get(film.getId()) != null) {
            filmsMap.put(film.getId(), film);
            return film;
        } else throw new HandleNotFoundException("Film Update Error");
    }

    public Film getFilmById(int id) {
        if (filmsMap.get(id) != null)
            return filmsMap.get(id);
        throw new HandleNotFoundException("Film Not Found");
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmsMap.values());
    }
}