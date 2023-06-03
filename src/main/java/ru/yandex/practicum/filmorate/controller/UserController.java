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
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private Integer id = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    private void generateId(User user) {
        ++id;
        user.setId(id);
    }

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private ArrayList<User>getUsers() {
         return new ArrayList<>(usersMap.values());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return getUsers();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidException {
        if (usersMap.get(user.getId()) != null) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            usersMap.put(user.getId(), user);
            log.info("Обновление пользователя id: " + user.getId());
            return user;
        } else {
            log.info("Ошибка обновления пользователя id: " + user.getId() + ", пользователь не найден");
            throw new ValidException("InvalidUser");
        }
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        generateId(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        usersMap.put(user.getId(), user);
        log.info("Добавлен пользователь id: " + user.getId());
        return user;
    }

}
