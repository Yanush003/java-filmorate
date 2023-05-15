package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.List;
import java.util.Set;

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

    public void putLike(Integer id, Integer like) {
        storage.putLike(id, like);
    }

    public void deleteLike(Integer id, Integer userId) {
        storage.deleteLike(id, userId);
    }

    public Set<Film> getFilmsSet(Integer count) {
        return storage.getFilmsSet(count);
    }

    public Film saveFilm(Film film) {
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
