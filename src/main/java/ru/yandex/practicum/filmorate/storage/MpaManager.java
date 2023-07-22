package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaManager {
    List<Mpa> getMpa();

    Mpa getMpa(int id);

    List<Integer> getMpaIds();
}
