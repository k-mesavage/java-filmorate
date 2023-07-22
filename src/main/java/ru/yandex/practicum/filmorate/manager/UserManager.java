package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserManager {
    List<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

    void addToFriends(int id, int friendId);

    void deleteFromFriends(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int friendId);

    User getUserById(int id);

    int getMaxId();
}
