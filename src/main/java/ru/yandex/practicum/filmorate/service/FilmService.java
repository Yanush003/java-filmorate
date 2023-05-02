package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolationException;
import java.util.*;


@Service
public class FilmService {

    private Integer countId = 1;
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    public Film saveFilm(Film film) {
        film.setId(countId++);
        filmMap.put(film.getId(), film);
        log.info("Film Save " + film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (!filmMap.containsKey(film.getId())) {
            throw new ConstraintViolationException(Set.of());
        }
        Film film1 = filmMap.get(film.getId());
        film.setId(film1.getId());
        filmMap.put(film.getId(), film);
        log.info("Film Update " + film);
        return film;
    }

    public List<Film> getListFilm() {
        log.info("Get Films " + filmMap.values());
        return new ArrayList<>(filmMap.values());
    }

    public void clearFilms() {
        this.filmMap.clear();
        countId = 0;
    }

}
