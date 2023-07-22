package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.storage.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test.sql"})
class FilmDbManagerTest {

    @Autowired
    @Qualifier("FilmDbManager")
    private FilmManager manager;

    @Test
    public void testFilmManager() {
        assertNotNull(manager);
    }

    @Test
    public void getFilms() {
        assertThat(manager.getAllFilms().size()).isEqualTo(2);
    }

    @Test
    public void addFilm() {
        Film newFilm = new Film(0
                , "New Film Name"
                , "New Description"
                , LocalDate.of(1900, 10, 10)
                , 100
                , 2
                , null
                , new Mpa(1, "G")
                , new ArrayList<>(Arrays.asList(new Genre(1, "Комедия"), new Genre(2, "Драма"))));
        manager.addFilm(newFilm);
        assertThat(manager.getFilmById(3)).hasFieldOrPropertyWithValue("name", "New Film Name");
    }

    @Test
    public void updateFilm() {
        Film updatedFilm = new Film(1
                , "Updated Film Name"
                , "Updated Description"
                , LocalDate.of(2000, 11, 11)
                , 100
                , 2
                , null
                , new Mpa(1, "G")
                , new ArrayList<>(List.of(new Genre(1, "Комедия"))));
        manager.updateFilm(updatedFilm);
        assertThat(manager.getFilmById(1))
                .hasFieldOrPropertyWithValue("name", "Updated Film Name");
    }

    @Test
    public void getFilmById() {
        assertThat(manager.getFilmById(1)).hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void addLike() {
        manager.addLike(1, 3);
        manager.addLike(1, 2);
        manager.addLike(2, 3);
        assertThat(manager.getMostLiked("3").get(0))
                .hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void deleteLike() {
        manager.deleteLike(1, 3);
        manager.deleteLike(1, 2);
        assertThat(manager.getMostLiked("3").get(0))
                .hasFieldOrPropertyWithValue("name", "Film Name2");
    }

    @Test
    public void getMostLiked() {
        assertThat(manager.getMostLiked("3").get(0))
                .hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void getMaxId() {
        assertThat(manager.getMaxId()).isEqualTo(2);
    }
}
