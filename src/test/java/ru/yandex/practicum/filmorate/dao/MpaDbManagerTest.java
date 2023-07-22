package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.manager.MpaManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test.sql"})
class MpaDbManagerTest {

    @Autowired
    MpaManager manager;

    @Test
    public void testMpaManager() {
        assertNotNull(manager);
    }

    @Test
    public void getMpaById() {
        assertThat(manager.getMpaById(1)).hasFieldOrPropertyWithValue("name", "G");
        assertThat(manager.getMpaById(2)).hasFieldOrPropertyWithValue("name", "PG");
        assertThat(manager.getMpaById(3)).hasFieldOrPropertyWithValue("name", "PG-13");
        assertThat(manager.getMpaById(4)).hasFieldOrPropertyWithValue("name", "R");
        assertThat(manager.getMpaById(5)).hasFieldOrPropertyWithValue("name", "NC-17");
    }

    @Test
    public void getMpa() {
        assertThat(manager.getMpa().size()).isEqualTo(5);
    }

    @Test
    public void getMpaIds() {
        assertThat(manager.getMpaIds().size()).isEqualTo(5);
    }
}
