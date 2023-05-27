package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.HashSet;
import java.util.List;

@Service
public class FilmService {

    private final InMemoryFilmStorage storage;

    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    public Film getFilmById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return storage.getFilm(id);
    }

    public void putLike(Integer id, Long like) {
        storage.putLike(id, like);
    }

    public void deleteLike(Integer id, Long userId) {
        storage.deleteLike(id, userId);
    }

    public List<Film> getFilms(Integer count) {
        return storage.getFilms(count);
    }

    public Film saveFilm(Film film) {
        if (film.getUserIds() == null) {
            film.setUserIds(new HashSet<>());
        }
        return storage.saveFilm(film);
    }

    public Film updateFilm(Film film) {
        return storage.updateFilm(film);
    }

    public List<Film> getListFilm() {
        return storage.getListFilm();
    }

    public void clearFilms() {
        storage.clearFilms();
    }

}
