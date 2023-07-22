package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.manager.UserManager;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    UserManager users;
    UserValidator validator;

    @Autowired
    private UserService(@Qualifier("UserDbManager") UserManager userManager, UserValidator validator) {
        this.users = userManager;
        this.validator = validator;
    }

    public User addUser(User user) {
        validator.validate(user);
        users.addUser(user);
        log.info("Add User {}", user.getId());
        return users.getUserById(users.getMaxId());
    }

    public User updateUser(User user) {
        validator.validate(user);
        if (users.getUserById(user.getId()) != null) {
            users.updateUser(user);
            log.info("Update User {}", user.getId());
        }
        else throw new NotFoundException("UpdateUserException");
        return users.getUserById(user.getId());
    }

    public void deleteUser(int id) {
        users.deleteUser(id);
    }

    public User getUserById(int id) {
        return users.getUserById(id);
    }

    public User addToFriends(int id, int friendId) {
        if (users.getUserById(id) != null && users.getUserById(friendId) != null) {
            users.addToFriends(id, friendId);
            log.info("Add To Friends {}, {}", id, friendId);
            return users.getUserById(id);
        } else throw new NotFoundException("User Not Found");
    }

    public User deleteFromFriends(int id, int friendId) {
        users.deleteFromFriends(id, friendId);
        log.info("Delete From Friends {}, {}", id, friendId);
        return users.getUserById(id);
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
