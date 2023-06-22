package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService manager;


    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен список пользователей");
        return manager.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return manager.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {
        log.info("Получен список друзей пользователя {}", id);
        return manager.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommon(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен список общих друзей {} и {}", id, otherId);
        return manager.getCommonFriends(id, otherId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        manager.addUser(user);
        log.info("Добавлен пользователь {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidException {
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
        log.info("Добавление в друзья");
        manager.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("Удаление из друзей");
        manager.deleteFriend(id, friendId);
    }
}
