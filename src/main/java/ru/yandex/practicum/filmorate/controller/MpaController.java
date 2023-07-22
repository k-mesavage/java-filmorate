package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService service;

    @GetMapping
    public List<Mpa> getRatings() {
        val answer = service.getMpa();
        log.info("Get MPAs: {}", answer);
        return answer;
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        val answer = service.getMpaById(id);
        log.info("Get MPA: {}", answer);
        return answer;
    }
}