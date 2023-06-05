package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.exeption.ValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserManager implements UserManager {

    private Integer id = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    private void generateId(User user) {
        ++id;
        user.setId(id);
    }

    private List<User> getAll() {
        return new ArrayList<>(usersMap.values());
    }

    private User getUser(int id) {
        return usersMap.get(id);
    }

    private void addUser(int id, User user) {
        usersMap.put(id, user);
    }

    @Override
    public User addUser(User user) {
        generateId(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        addUser(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (getUser(user.getId()) != null) {
            addUser(user.getId(), user);
            return user;
        }
        else {
            throw new ValidException("InvalidUser");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return getAll();
    }
}
