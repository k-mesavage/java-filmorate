package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Integer id = 0;
    private HashMap<Integer, User> usersMap = new HashMap<>();

    private void idGenerator(User user) {
        ++id;
        user.setId(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(usersMap.values());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidException {
        if (usersMap.get(user.getId()) != null) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            usersMap.put(user.getId(), user);
            return user;
        } else {
            throw new ValidException("InvalidUser");
        }
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        idGenerator(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        usersMap.put(user.getId(), user);
        return user;
    }

}
