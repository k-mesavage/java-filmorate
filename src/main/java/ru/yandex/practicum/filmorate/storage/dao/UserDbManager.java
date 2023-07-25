package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.storage.UserManager;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component("UserDbManager")
public class UserDbManager implements UserManager {
    private static final String GET_USERS = "SELECT * FROM users";
    private static final String ADD_USER = "INSERT INTO users(name, email, login, birthday) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String ADD_TO_FRIENDS = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?)";
    private static final String DELETE_FROM_FRIENDS = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE user_id = ";
    private static final String GET_MAX_ID = "SELECT max(user_id) as maxId FROM users;";

    private final JdbcTemplate jdbcTemplate;

    Integer id = 0;

    public UserDbManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void generateId(User user) {
        ++id;
        user.setId(id);
    }

    @Override
    public List<User> getAllUsers() {
        return getUsers(GET_USERS);
    }

    @Override
    public User addUser(User user) {
        generateId(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        jdbcTemplate.update(ADD_USER, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        return user;
    }

    public User getUserById(int id) {
        try {
            return jdbcTemplate.query(GET_USER_BY_ID + id, (rs, rowNum) -> createUser(rs)).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("User Not Found");
        }
    }

    @Override
    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        try {
            jdbcTemplate.update(UPDATE_USER,
                    user.getName(),
                    user.getEmail(),
                    user.getLogin(),
                    user.getBirthday(),
                    user.getId());
            return user;
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("User Not Found");
        }
    }

    @Override
    public void deleteUser(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    public void addToFriends(int id, int friendId) {
        jdbcTemplate.update(ADD_TO_FRIENDS, id, friendId);
    }

    public void deleteFromFriends(int id, int friendId) {
        jdbcTemplate.update(DELETE_FROM_FRIENDS, id, friendId);
    }

    public List<User> getFriends(int id) {
        final String GET = "SELECT u.* FROM users u JOIN friends f ON u.user_id = f.friend_id " +
                "WHERE f.user_id = " +
                id + " UNION SELECT u.* FROM users u JOIN friends f ON u.user_id = f.user_id WHERE f.friend_id = " +
                id + " AND f.user_id = " +
                id;
        return getFriends(GET);
    }

    public List<User> getCommonFriends(int id, int friendId) {
        final String sql = "SELECT u.* " +
                "FROM users u " +
                "JOIN friends f ON u.user_id = f.friend_id " +
                "WHERE (f.user_id = " +
                id + " OR f.user_id = " +
                friendId + ") AND NOT (f.friend_id = " +
                id + " OR f.friend_id = " +
                friendId + ") GROUP BY u.user_id";
        return getFriends(sql);
    }

    public int getMaxId() {
        final Integer maxId = jdbcTemplate.queryForObject(GET_MAX_ID, (rs, rowNum) -> rs.getInt("maxId"));
        if (maxId != null) {
            return maxId;
        }
        return 0;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate());
    }

    private List<User> getFriends(String sql) {
        return getUsers(sql);
    }

    @NotNull
    private List<User> getUsers(String SQL_STR) {
        final List<User> friends = new ArrayList<>();
        final Optional<List<User>> friendsOpt = jdbcTemplate.query(SQL_STR, (rs, rowNum) -> {
            do {
                friends.add(createUser(rs));
            } while (rs.next());
            return friends;
        }).stream().findFirst();
        return friendsOpt.orElse(new ArrayList<>());
    }
}