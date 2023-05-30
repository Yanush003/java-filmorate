package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    Film get(Long id);

    void delete(Long id);

    List<Film> getAll();
}
