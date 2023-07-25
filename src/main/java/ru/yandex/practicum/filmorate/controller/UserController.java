package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService manager;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Add User id: " + user.getId());
        return manager.addUser(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("Get User id: {}", id);
        return manager.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return manager.getAllUsers();
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Update User id: " + user.getId());
        return manager.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        log.info("Request to delete user: #{}", id);
        manager.deleteUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Request to make users friends: #{} and #{}", id, friendId);
        manager.addToFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable Integer id) {
        log.info("Request to find friends of user #{}", id);
        return manager.getFriends(id);

    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Request to find common friends for user: #{} and #{}", id, otherId);
        return manager.getCommonFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Request to destroy friendship between users: #{} and #{}", id, friendId);
        manager.deleteFromFriends(id, friendId);
    }
}
