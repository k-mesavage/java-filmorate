package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.manager.InMemoryUserManager;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends InMemoryUserManager {

    private static final InMemoryUserManager manager = new InMemoryUserManager();

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public List<User> getAllUsers() {
        return manager.getAllUsers();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidException {
        if (manager.updateUser(user) == user) {
            log.info("Обновление пользователя id: " + user.getId());
            return user;
        } else {
            log.info("Ошибка обновления пользователя id: " + user.getId() + ", пользователь не найден");
        }
        return user;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        manager.addUser(user);
        log.info("Добавлен пользователь id: " + user.getId());
        return user;
    }
}
