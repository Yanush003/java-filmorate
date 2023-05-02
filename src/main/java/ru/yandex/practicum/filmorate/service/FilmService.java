package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FilmService {

    private Integer countId = 0;

    private Map<Integer, Film> filmMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    public Film saveFilm(Film film) {
        log.info("Film Save");
        film.setId(countId++);
        filmMap.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        Film film1 = filmMap.get(film.getId());
        film.setId(film1.getId());
        filmMap.put(film.getId(), film);
        return film;
    }

    public List<Film> getListFilm() {
        return new ArrayList<>(filmMap.values());
    }

    public void clearFilms() {
        this.filmMap.clear();
        countId = 0;
    }

}
