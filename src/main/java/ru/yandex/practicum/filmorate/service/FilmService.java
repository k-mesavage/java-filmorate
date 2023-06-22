package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FilmService {
    private InMemoryFilmStorage inMemoryFilmStorage;
    private InMemoryUserStorage inMemoryUserStorage;

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
        film.getLikes().add(userId);
        inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteLike(int filmId, int userId) {
        if (inMemoryUserStorage.getUserById(userId) != null) {
            Film film = inMemoryFilmStorage.getFilmById(filmId);
            film.getLikes().remove(userId);
            inMemoryFilmStorage.updateFilm(film);
        } else throw new HandleNotFoundException("User Not Found");
    }

    public Set<Film> getPopularFilms(Integer count) {
        if (inMemoryFilmStorage.getAllFilms().size() != 0)
            return inMemoryFilmStorage.getAllFilms()
                    .stream()
                    .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                    .limit(count)
                    .collect(Collectors.toSet());
        return Set.of();
    }
}
