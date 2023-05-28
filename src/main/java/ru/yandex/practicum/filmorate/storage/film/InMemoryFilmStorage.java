package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final InMemoryUserStorage userStorage;

    private final Map<Long, Film> films = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(FilmService.class);
    private Long countId = 1L;

    public Film get(Long id) {
        if (Objects.isNull(films.get(id))) throw new NotFoundException("not found");
        return films.get(id);
    }

    @Override
    public void delete(Long id) {
        films.remove(id);
    }

    public void putLike(Long id, Long userId) {
        Film film = films.get(id);
        film.getUserIds().add(userId);
    }

    public void deleteLike(Long id, Long userId) {
        userStorage.get(userId);
        Film film = get(id);
        film.getUserIds().remove(userId);

    }

    public List<Film> getFilms(Long count) {
        return films.values().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getUserIds().size(), film1.getUserIds().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film create(Film film) {
        film.setId(countId++);
        films.put(film.getId(), film);
        log.info("Film Save " + film);
        return film;
    }

    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("not found");
        }
        Film oldFilm = films.get(film.getId());
        oldFilm.setName(film.getName());
        oldFilm.setReleaseDate(film.getReleaseDate());
        oldFilm.setDuration(film.getDuration());
        oldFilm.setDescription(film.getDescription());
        log.info("Film Update " + film);
        return oldFilm;
    }

    public List<Film> getAll() {
        log.info("Get Films " + films.values());
        return new ArrayList<>(films.values());
    }

    public void clearFilms() {
        this.films.clear();
        countId = 0L;
    }


}
