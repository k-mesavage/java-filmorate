package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService manager;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        manager.addUser(user);
        log.info("Add User id: " + user.getId());
        return user;
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
        if (manager.updateUser(user) == user) {
            log.info("Update User id: " + user.getId());
            return user;
        } else {
            log.info("Update User Error id: " + user.getId());
        }
        return user;
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
        List<User> friends = manager.getFriends(id);
        log.info("Found {} friends, Friend's ID: {}", friends.size(),
                friends.stream().map(User::getId).collect(Collectors.toList()));
        return friends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Request to find common friends for user: #{} and #{}", id, otherId);
        List<User> commonFriends = manager.getCommonFriends(id, otherId);
        log.info("Found {} common friends, Friend's ID: {}", commonFriends.size(),
                commonFriends.stream().map(User::getId).collect(Collectors.toList()));
        return commonFriends;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Request to destroy friendship between users: #{} and #{}", id, friendId);
        manager.deleteFromFriends(id, friendId);
    }
}
