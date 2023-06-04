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
import ru.yandex.practicum.filmorate.manager.UserManager;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private static final UserManager userManager = new UserManager();

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userManager.getAllUsers();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidException {
        if (userManager.updateUser(user)) {
            log.info("Обновление пользователя id: " + user.getId());
            return user;
        } else {
            log.info("Ошибка обновления пользователя id: " + user.getId() + ", пользователь не найден");
            throw new ValidException("InvalidUser");
        }
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        userManager.addUser(user);
        log.info("Добавлен пользователь id: " + user.getId());
        return user;
    }
}
