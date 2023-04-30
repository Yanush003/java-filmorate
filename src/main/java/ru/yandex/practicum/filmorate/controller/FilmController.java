package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/film")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping("/save")
    public Film saveFilm(@Valid @RequestBody Film film) {
        return filmService.saveFilm(film);
    }

    @PutMapping("/update")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/get")
    public List<Film> getListFilm() {
        return filmService.getListFilm();
    }


}
