package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface FilmStorage {
    Film getFilm(Integer id);

    void putLike(Integer id, Integer userId);

    void deleteLike(Integer id, Integer userId);

    Set<Film> getFilmsSet(Integer count);
}
