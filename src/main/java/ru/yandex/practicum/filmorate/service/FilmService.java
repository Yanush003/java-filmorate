package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;


@Service
public class FilmService {
    private Film film;
    private List<Film> films = new ArrayList<>();
    private final static Logger log = LoggerFactory.getLogger(FilmService.class);

    public Film saveFilm(Film film) {
        log.info("Film Save");
        film.setId(1);
        this.film = film;
        films.add(film);
        return this.film;
    }

    public Film updateFilm(Film film) {
        this.film.setName(film.getName());
        return this.film;
    }

    public List<Film> getListFilm() {
        return this.films;
    }

}
