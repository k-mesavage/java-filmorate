package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.HandleNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Integer id = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    private void generateId(User user) {
        ++id;
        user.setId(id);
    }

    public User getUserById(int id) {
        if (usersMap.get(id) != null) {
            return usersMap.get(id);
        } else throw new HandleNotFoundException("User Not Found");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

    public User addUser(User user) {
        generateId(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        usersMap.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (getUserById(user.getId()) != null) {
            usersMap.put(user.getId(), user);
            return user;
        } else throw new HandleNotFoundException("User Update Error");
    }
}
