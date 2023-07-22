package ru.yandex.practicum.filmorate.manager.InMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.manager.UserManager;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component("InMemoryUserStorage")
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
        } else {
            throw new ValidationException("InvalidUser");
        }
    }

    @Override
    public void deleteUser(int id) {
        usersMap.remove(id);
    }

    @Override
    public void addToFriends(int id, int friendId) {
        usersMap.get(id).getFriends().add(friendId);
        usersMap.get(friendId).getFriends().add(id);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        usersMap.get(id).getFriends().remove(friendId);
        usersMap.get(friendId).getFriends().remove(id);
    }

    @Override
    public List<User> getFriends(int id) {
        Set<Integer> friendsIds = usersMap.get(id).getFriends();
        List<User> answer = new ArrayList<>();
        for (int friendId: friendsIds) {
            answer.add(usersMap.get(friendId));
        }
        return answer;
    }

    @Override
    public List<User> getCommonFriends(int id, int friendId) {
        return usersMap.values().stream()
                .filter(user -> user.getFriends().contains(id) && user.getFriends().contains(friendId))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(int id) {
        return usersMap.get(id);
    }

    @Override
    public int getMaxId() {
        return usersMap.size();
    }

    @Override
    public List<User> getAllUsers() {
        return getAll();
    }
}
