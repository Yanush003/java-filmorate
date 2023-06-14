package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FilmService {

    private final FilmDbStorage storage;
    private final UserDbStorage userDbStorage;

    public FilmService(FilmDbStorage storage, UserDbStorage userDbStorage) {
        this.storage = storage;
        this.userDbStorage = userDbStorage;
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
        checkAndGetUserIsExisting(userId);
        Film film = storage.get(id);
        film.getUserIds().remove(userId);
        storage.update(film);
    }

    public List<Film> getFilms(Integer count) {
        List<Film> films = storage.getAll();
        for (Film film : films) {
            film.setUserIds(userDbStorage.getByFilms(film.getId()));
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

    public void checkAndGetUserIsExisting(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        userDbStorage.get(id);
    }

}
