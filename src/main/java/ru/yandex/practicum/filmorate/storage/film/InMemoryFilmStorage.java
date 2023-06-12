package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        Objects.requireNonNull(film, "User cannot be null");
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Objects.requireNonNull(film, "User cannot be null");
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    public Film get(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (!films.containsKey(id)) {
            throw new NotFoundException("Film not found with ID: " + id);
        }
        return films.get(id);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        films.remove(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

}
