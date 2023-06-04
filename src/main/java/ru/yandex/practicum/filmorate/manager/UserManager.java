package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager extends Manager {

    private Integer id = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    private void generateId(User user) {
        ++id;
        user.setId(id);
    }

    private ArrayList<User> getAll() {
        return new ArrayList<>(usersMap.values());
    }

    private User getUser(int id) {
        return usersMap.get(id);
    }

    private void addUser(int id, User user) {
        usersMap.put(id, user);
    }

    @Override
    public void addUser(User user) {
        generateId(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        addUser(user.getId(), user);
    }

    @Override
    public Boolean updateUser(User user) {
        if (getUser(user.getId()) != null) {
            addUser(user.getId(), user);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return getAll();
    }
}
