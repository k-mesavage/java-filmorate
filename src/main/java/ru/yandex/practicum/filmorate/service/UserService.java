package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.storage.UserManager;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserManager users;

    @Autowired
    private UserService(@Qualifier("UserDbManager") UserManager userManager) {
        this.users = userManager;
    }

    public User addUser(User user) {
        users.addUser(user);
        log.info("Add User {}", user.getId());
        return users.getUserById(users.getMaxId());
    }

    public User updateUser(User user) {
        if (users.getUserById(user.getId()) != null) {
            users.updateUser(user);
            log.info("Update User {}", user.getId());
        } else throw new NotFoundException("UpdateUserException");
        return users.getUserById(user.getId());
    }

    public void deleteUser(int id) {
        users.deleteUser(id);
    }

    public User getUserById(int id) {
        return users.getUserById(id);
    }

    public void addToFriends(int id, int friendId) {
        if (users.getUserById(id) != null && users.getUserById(friendId) != null) {
            users.addToFriends(id, friendId);
            log.info("Add To Friends {}, {}", id, friendId);
        } else throw new NotFoundException("User Not Found");
    }

    public void deleteFromFriends(int id, int friendId) {
        users.deleteFromFriends(id, friendId);
        log.info("Delete From Friends {}, {}", id, friendId);
    }

    public List<User> getFriends(int id) {
        log.info("Get friends {}", id);
        return users.getFriends(id);
    }

    public List<User> getCommonFriends(int id, int friendId) {
        log.info("Get Common Friends {}, {}", id, friendId);
        return users.getCommonFriends(id, friendId);
    }

    public List<User> getAllUsers() {
        log.info("Get All Users");
        return new ArrayList<>(users.getAllUsers());
    }
}
