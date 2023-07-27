package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Mpa;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.storage.FilmManager;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("FilmDbManager")
@RequiredArgsConstructor
public class FilmDbManager implements FilmManager {

    private static final String GET_FILMS = "SELECT * FROM films";
    private static final String ADD_FILM = "INSERT INTO films(name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_FILM = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ? WHERE film_id = ?";
    private static final String UPDATE_FILM_GENRE_DEL = "DELETE FROM film_genre_list WHERE film_id = ?";
    private static final String UPDATE_FILM_GENRE_ADD = "INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)";
    private static final String GET_FILM_BY_ID = "SELECT * FROM films WHERE film_id=";
    private static final String SET_LIKE = "INSERT INTO likes(film_id, user_id) VALUES(?, ?)";
    private static final String SET_LIKE_UPDATE_FILM_RATE = "UPDATE films SET rate = rate + 1 WHERE film_id = ?";
    private static final String DELETE_LIKE = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String DELETE_LIKE_UPDATE_FILM_RATE = "UPDATE films SET rate = rate - 1 WHERE film_id = ?";
    private static final String GET_MOST_LIKED = "SELECT * FROM films ORDER BY rate DESC LIMIT ";
    private static final String GET_MAX_ID = "SELECT max(film_id) AS maxId FROM films";
    private static final String CREATE_LIKES = "SELECT user_id FROM likes WHERE film_id = ";
    private static final String CREATE_RATING = "SELECT * FROM rating WHERE rating_id=";
    private static final String CREATE_GENRES = "SELECT fgl.genre_id, g.genre_name FROM film_genre_list fgl JOIN genre g ON g.genre_id = fgl.genre_id" + " WHERE fgl.film_id =";

    private final JdbcTemplate jdbcTemplate;

    private final UserDbManager userDbManager;

    private Integer id = 0;

    @Override
    public void generateId(Film film) {
        ++id;
        film.setId(id);
    }

    @Override
    public List<Film> getAllFilms() {
        final List<Film> films = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(GET_FILMS, (rs, rowNum) -> {
                do {
                    films.add(createFilm(rs));
                } while (rs.next());
                return films;
            });
        } catch (EmptyResultDataAccessException e) {
            return films;
        }
    }

    @Override
    public Film addFilm(Film film) {
        jdbcTemplate.update(ADD_FILM, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate(), film.getMpa().getId());
        generateId(film);
        if (film.getGenres() != null) {
            film.getGenres().stream()
                    .map(Genre::getId)
                    .distinct()
                    .forEach(integer -> jdbcTemplate.update(UPDATE_FILM_GENRE_ADD, film.getId(), integer));
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update(UPDATE_FILM,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getMpa().getId(), film.getId());
        if (film.getGenres() != null) {
            jdbcTemplate.update(UPDATE_FILM_GENRE_DEL, film.getId());
            film.getGenres().stream()
                    .map(Genre::getId)
                    .distinct()
                    .forEach(integer -> jdbcTemplate.update(UPDATE_FILM_GENRE_ADD, film.getId(), integer));
        }
        return getFilmById(film.getId());
    }

    @Override
    public Film getFilmById(int id) {
        try {
            return jdbcTemplate.query(GET_FILM_BY_ID + id, (rs, rowNum) -> createFilm(rs)).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Film Not Found");
        }
    }

    private Film createFilm(ResultSet rs) throws SQLException {
        return new Film(rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"), rs.getInt("rate"),
                createLikes(rs.getInt("film_id")),
                createMpa(rs.getInt("rating_id")),
                createGenres(rs.getInt("film_id")));
    }

    private Set<Integer> createLikes(int id) {
        final Set<Integer> likes = new HashSet<>();
        try {
            return jdbcTemplate.queryForObject(CREATE_LIKES + id, (rs, rowNum) -> {
                do {
                    likes.add(rs.getInt("user_id"));
                } while (rs.next());
                return likes;
            });
        } catch (EmptyResultDataAccessException e) {
            return likes;
        }
    }

    private void setLike(String SQL_STR_LIKE, String SQL_STR_FILM, int id, int userId) {
        jdbcTemplate.update(SQL_STR_LIKE, id, userId);
        jdbcTemplate.update(SQL_STR_FILM, id);
    }

    public void addLike(int id, int userId) {
        setLike(SET_LIKE, SET_LIKE_UPDATE_FILM_RATE, id, userId);
    }

    public void deleteLike(int id, int userId) {
        setLike(DELETE_LIKE,
                DELETE_LIKE_UPDATE_FILM_RATE,
                getFilmById(id).getId(),
                userDbManager.getUserById(userId).getId());
    }

    public List<Film> getMostLiked(String countParam) {
        final List<Film> mostLiked = new ArrayList<>();
        final Optional<List<Film>> mostLikedOpt = jdbcTemplate.query(GET_MOST_LIKED + countParam, (rs, rowNum) -> {
            do {
                mostLiked.add(createFilm(rs));
            } while (rs.next());
            return mostLiked;
        }).stream().findFirst();

        return mostLikedOpt.orElse(new ArrayList<>());
    }

    public int getMaxId() {
        final Integer maxId = jdbcTemplate.queryForObject(GET_MAX_ID, (rs, rowNum) -> rs.getInt("maxId"));
        if (maxId != null) {
            return maxId;
        }
        return 0;
    }

    private Mpa createMpa(int id) {
        return jdbcTemplate.queryForObject(CREATE_RATING + id, (rs, rowNum) ->
                new Mpa(rs.getInt("rating_id"), rs.getString("rating_name")));
    }

    private List<Genre> createGenres(int id) {
        final List<Genre> genres = new ArrayList<>();
        final Optional<List<Genre>> genresOpt = jdbcTemplate.query(CREATE_GENRES + id, (rs, rowNum) -> {
            do {
                genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
            } while (rs.next());

            final HashMap<Integer, Genre> map = new HashMap<>();
            for (Genre genre : genres) {
                int genreId = genre.getId();
                map.putIfAbsent(genreId, genre);
            }

            List<Genre> result = new ArrayList<>(map.values());
            result.sort(Comparator.comparingInt(Genre::getId));
            return result;
        }).stream().findFirst();

        return genresOpt.orElse(new ArrayList<>());
    }
}
