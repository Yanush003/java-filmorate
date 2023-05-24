package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    Film getFilm(Integer id);

    void putLike(Integer id, Long userId);

    void deleteLike(Integer id, Long userId);

    List<Film> getFilms(Integer count);
}
