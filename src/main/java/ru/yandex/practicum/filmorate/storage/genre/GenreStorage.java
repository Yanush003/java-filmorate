package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    Genre create(Genre genre);

    Genre update(Genre genre);

    List<GenreDto> getGenre(Long id);

    void delete(Long id);

    List<Genre> getGenres();
}
