package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;

@Service
public class MpaService {

    private final MpaDbStorage storage;

    public MpaService(MpaDbStorage storage) {
        this.storage = storage;
    }

    public void setMpa(Long idFilm, Long idMpa) {
        storage.setMpa(idFilm, idMpa);
    }

    public void updateMpa(Long idFilm, Long idMpa) {
        storage.updateMpa(idFilm, idMpa);
    }

    public Mpa getMpa(Long filmId) {
        return storage.getMpa(filmId);
    }

    public List<Mpa> getAll() {
        return storage.getAll();
    }

    public Mpa getMpaByID(Long id) {
        return storage.getMpaById(id);
    }
}
