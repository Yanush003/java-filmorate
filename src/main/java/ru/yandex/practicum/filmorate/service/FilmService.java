package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class FilmService {

    private final FilmDbStorage storage;
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(FilmService.class);


    public FilmService(FilmDbStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    public Film getFilmById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return storage.get(id);
    }

    public void putLike(Long id, Long like) {
        Film film = storage.get(id);
        film.getUserIds().add(like);
        storage.update(film);
    }

    public void deleteLike(Long id, Long userId) {
        userService.checkAndGetUserIsExisting(userId);
        Film film = storage.get(id);
        film.getUserIds().remove(userId);
        storage.update(film);
    }

    public List<Film> getFilms(Integer count) {
        List<Film> films = storage.getAll();
        for (Film film : films) {
            film.setUserIds(userService.getByFilms(film.getId()));
        }

        films.sort((film1, film2) -> Integer.compare(film2.getUserIds().size(), film1.getUserIds().size()));
        List<Film> filmSort = new ArrayList<>();
        for (int i = 0; i < count && i < films.size(); i++) {
            filmSort.add(films.get(i));
        }
        return filmSort;
    }

    public List<Film> getAll() {
        return storage.getAll();
    }

    public Film saveFilm(Film film) {
        if (film.getUserIds() == null) {
            film.setUserIds(new HashSet<>());
        }

        log.info("Film Save " + film);
        return storage.create(film);
    }

    public Film updateFilm(Film film) {

        storage.get(film.getId());
        log.info("Film Update " + film);
        return storage.update(film);
    }

}
