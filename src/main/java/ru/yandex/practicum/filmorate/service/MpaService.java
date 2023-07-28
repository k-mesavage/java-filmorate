package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.MpaManager;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final MpaManager manager;

    @Autowired
    public MpaService(MpaManager manager) {
        this.manager = manager;
    }

    public List<Mpa> getMpa() {
        return manager.getMpa();
    }

    public Mpa getMpaById(int id) {
        return manager.getMpa(id);
    }
}
