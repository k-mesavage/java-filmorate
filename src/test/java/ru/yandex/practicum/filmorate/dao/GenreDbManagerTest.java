package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.manager.GenreManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test.sql"})
class GenreDbManagerTest {

    @Autowired
    private GenreManager manager;

    @Test
    public void testGenres() {
        assertNotNull(manager);
    }

    @Test
    public void getGenreById() {
        assertThat(manager.getGenreById(1)).hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(manager.getGenreById(2)).hasFieldOrPropertyWithValue("name", "Драма");
        assertThat(manager.getGenreById(3)).hasFieldOrPropertyWithValue("name", "Мультфильм");
        assertThat(manager.getGenreById(4)).hasFieldOrPropertyWithValue("name", "Триллер");
        assertThat(manager.getGenreById(5)).hasFieldOrPropertyWithValue("name", "Документальный");
        assertThat(manager.getGenreById(6)).hasFieldOrPropertyWithValue("name", "Боевик");
    }

    @Test
    public void getGenres() {
        assertThat(manager.getAllGenres().size()).isEqualTo(6);
    }
}
