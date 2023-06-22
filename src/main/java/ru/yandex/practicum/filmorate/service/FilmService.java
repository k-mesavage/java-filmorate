package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.HandleNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public InMemoryFilmStorage inMemoryFilmStorage;

    public InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(inMemoryFilmStorage.getAllFilms());
    }

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public void addLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.likes.add(userId);
        inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteLike(int filmId, int userId) {
        if (inMemoryUserStorage.getUserById(userId) != null) {
            Film film = inMemoryFilmStorage.getFilmById(filmId);
            film.likes.remove(userId);
            inMemoryFilmStorage.updateFilm(film);
        } else throw new HandleNotFoundException("User Not Found");
    }

    public Set<Film> getPopularFilms(Integer count) {
        if (inMemoryFilmStorage.getAllFilms().size() != 0)
            return inMemoryFilmStorage.getAllFilms()
                    .stream()
                    .sorted((f1, f2) -> f2.likes.size() - f1.likes.size())
                    .limit(count)
                    .collect(Collectors.toSet());
        return Set.of();
    }
}
