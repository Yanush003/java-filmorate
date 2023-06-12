package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    Mpa create(Mpa mpa);

    void updateMpa(Long idFilm, Long idMpa);

    Mpa getMpa(Long id);

    void delete(Long id);

    List<Mpa> getAll();


}
