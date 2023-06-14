package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Service
public class GenreService {

    private final GenreDbStorage storage;

    public GenreService(GenreDbStorage storage) {
        this.storage = storage;
    }

    public List<Genre> getGenres() {
        return storage.getGenres();
    }

    public List<GenreDto> getGenre(Long id) {
        return storage.getGenre(id);
    }

    public Genre getById(Long id) {
        return storage.getById(id);
    }

    public void addGenres(Long filmId, List<Long> genreIds) {
        storage.addGenres(filmId, genreIds);
    }

    public void updateGenre(List<GenreDto> genreDtos, Long filmId) {
        storage.updateGenre(genreDtos, filmId);
    }
}

