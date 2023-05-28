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

    public Film getFilmById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return storage.get(id);
    }

    public void putLike(Long id, Long like) {
        storage.putLike(id, like);
    }

    public void deleteLike(Long id, Long userId) {
        storage.deleteLike(id, userId);
    }

    public List<Film> getFilms(Long count) {
        return storage.getFilms(count);
    }

    public Film saveFilm(Film film) {
        if (film.getUserIds() == null) {
            film.setUserIds(new HashSet<>());
        }
        return storage.create(film);
    }

    public Film updateFilm(Film film) {
        return storage.update(film);
    }

    public List<Film> getListFilm() {
        return storage.getAll();
    }

    public void clearFilms() {
        storage.clearFilms();
    }

}
