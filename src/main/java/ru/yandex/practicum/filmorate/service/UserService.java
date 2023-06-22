package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.getAllUsers());
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void addFriend(int friend1Id, int friend2Id) {
        User friend1 = userStorage.getUserById(friend1Id);
        User friend2 = userStorage.getUserById(friend2Id);
        friend1.friends.add(friend2Id);
        friend2.friends.add(friend1Id);
    }

    public void deleteFriend(int friend1Id, int friend2Id) {
        User friend1 = userStorage.getUserById(friend1Id);
        User friend2 = userStorage.getUserById(friend2Id);
        friend1.friends.remove(friend2.getId());
        friend2.friends.remove(friend1.getId());
    }

    public Set<User> getCommonFriends(int friend1Id, int friend2Id) {
        User friend1 = userStorage.getUserById(friend1Id);
        User friend2 = userStorage.getUserById(friend2Id);
        return friend1.getFriends().stream()
                .filter(f -> friend2.getFriends().contains(f))
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    public Set<User> getFriends(int userId) {
        User user = userStorage.getUserById(userId);
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }
}
