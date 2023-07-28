package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.storage.MpaManager;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MpaDbManager implements MpaManager {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String GET_RATINGS = "SELECT * FROM rating";
    private static final String GET_RATING_BY_ID = "SELECT * FROM rating where rating_id = ";
    private static final String GET_MPA_IDS = "SELECT rating_id FROM rating";


    private Mpa createRating(ResultSet rs) {
        try {
            return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
        } catch (SQLException e) {
            throw new RuntimeException("Create Rating Exception");
        }
    }

    @Override
    public List<Mpa> getMpa() {
        final List<Mpa> ratings = new ArrayList<>();
        final Optional<List<Mpa>> ratingOpt = jdbcTemplate.query(GET_RATINGS, (rs, rowNum) -> {
            do {
                ratings.add(createRating(rs));
            } while (rs.next());
            return ratings;
        }).stream().findFirst();

        return ratingOpt.orElse(new ArrayList<>());
    }


    @Override
    public Mpa getMpa(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_RATING_BY_ID + id, (rs, rowNum) -> createRating(rs));
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Not Found Mpa Exception");
        }
    }

    @Override
    public List<Integer> getMpaIds() {
        List<Integer> mpaIds = new ArrayList<>();
        Optional<List<Integer>> mpaIdsOpt = jdbcTemplate.query(GET_MPA_IDS, (rs, rowNum) -> {
            do {
                mpaIds.add(rs.getInt("rating_id"));
            } while (rs.next());
            return mpaIds;
        }).stream().findFirst();

        return mpaIdsOpt.orElse(new ArrayList<>());
    }
}
