package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public interface UserStorage {

    List<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user) throws FileNotFoundException;
}
