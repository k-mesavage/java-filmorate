package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.storage.dao.UserDbManager;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test.sql"})
class UserDbManagerTest {

    @Autowired
    @Qualifier("UserDbManager")
    private UserDbManager manager;

    @Test
    public void testUserManager() {
        assertNotNull(manager);
    }

    @Test
    void getUsers() {
        assertThat(manager.getAllUsers().size()).isEqualTo(3);
    }

    @Test
    public void addUser() {
        User newUser = new User(0, "Login", "New user name", "newEmail@mail.com"
                , LocalDate.of(1900, 10, 10));
        manager.addUser(newUser);
        assertThat(manager.getAllUsers().get(3)).hasFieldOrPropertyWithValue("name", "New user name");
    }

    @Test
    public void updateUser() {
        User updateUser = new User(1, "Login", "Updated user name", "UpdatedEmail@mail.com"
                , LocalDate.of(1900, 10, 10));
        manager.updateUser(updateUser);
        assertThat(manager.getUserById(1)).hasFieldOrPropertyWithValue("name", "Updated user name");
    }

    @Test
    public void addToFriend() {
        manager.addToFriends(3, 1);
        assertThat(manager.getFriends(3).size()).isEqualTo(1);
    }

    @Test
    public void deleteFromFriends() {
        manager.deleteFromFriends(2, 1);
        assertThat(manager.getFriends(2).size()).isEqualTo(0);
    }

    @Test
    public void getFriends() {
        assertThat(manager.getFriends(1).size()).isEqualTo(2);
    }
    @Test
    public void getCommonFriends() {
        manager.addToFriends(2, 3);
        assertThat(manager.getCommonFriends(1, 2).size()).isEqualTo(1);
    }

    @Test
    public void getUserById() {
        assertThat(manager.getUserById(1)).hasFieldOrPropertyWithValue("name", "User Name1");
    }

    @Test
    public void getMaxId() {
        assertThat(manager.getMaxId()).isEqualTo(3);
    }
}
