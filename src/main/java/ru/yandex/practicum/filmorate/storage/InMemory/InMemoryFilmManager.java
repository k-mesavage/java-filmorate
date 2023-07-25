package ru.yandex.practicum.filmorate.storage.InMemory;

import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.storage.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryFilmManager implements FilmManager {

    private final Map<Integer, Film> filmsMap = new HashMap<>();
    private Integer id = 0;

    private List<Film> getAll() {
        return new ArrayList<>(filmsMap.values());
    }

    public Film getFilmById(int id) {
        return filmsMap.get(id);
    }

    private Film addFilm(int id, Film film) {
        filmsMap.put(id, film);
        return film;
    }

    @Override
    public void generateId(Film film) {
        ++id;
        film.setId(id);
    }

    @Override
    public void addLike(int id, int userId) {
        filmsMap.get(id).getLikes().add(userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        filmsMap.get(id).getLikes().remove(userId);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        if (count < 0) {
            throw new ValidationException("GetMostLikedException");
        }
        return filmsMap.values().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public int getMaxId() {
        return 0;
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
        if (getFilmById(film.getId()) != null) {
            addFilm(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("InvalidFilmUpdate");
        }
    }
}