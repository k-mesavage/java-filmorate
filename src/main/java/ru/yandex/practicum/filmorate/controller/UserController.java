package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService manager;

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    public UserController(UserService userService) {
        this.manager = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return manager.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return manager.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        return manager.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommon(@PathVariable int id, @PathVariable int otherId) {
        return manager.getCommonFriends(id, otherId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidException {
        if (manager.updateUser(user) == user) {
            log.info("Обновление пользователя {}", user);
            return user;
        } else {
            log.info("Ошибка обновления пользователя {}", user);
        }
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void putFriends(@PathVariable int id, @PathVariable int friendId) {
        manager.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        manager.deleteFriend(id, friendId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        manager.addUser(user);
        log.info("Добавлен пользователь {}", user);
        return user;
    }
}
