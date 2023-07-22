package ru.yandex.practicum.filmorate.manager;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaManager {
    List<Mpa> getMpa();

    Mpa getMpaById(int id);

    List<Integer> getMpaIds();
}
