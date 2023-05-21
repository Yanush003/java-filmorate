package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchCustomerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final InMemoryUserStorage userStorage;

    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, List<Integer>> likesFilm = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(FilmService.class);
    private Integer countId = 1;

    public Film getFilm(Integer id) {
        if (Objects.isNull(films.get(id))) throw new NoSuchCustomerException("not found");
        return films.get(id);
    }

    public void putLike(Integer id, Integer userId) {
        if (likesFilm.get(id).isEmpty()) {
            likesFilm.put(id, new ArrayList<>(userId));
        } else {
            List<Integer> likes = likesFilm.get(id);
            likes.add(userId);
            likesFilm.put(id, likes);
        }
    }


    public void deleteLike(Integer id, Integer userId) {
        if (Objects.isNull(likesFilm.get(userId))) throw new NoSuchCustomerException("not found");
        List<Integer> likes = likesFilm.get(userId);
        likes.remove(userId);
        likesFilm.put(id, likes);
    }

    public Set<Film> getFilmsSet(Integer count) {
        Map<Integer, List<Integer>> integerListMap = sortMap(likesFilm, count);
        return integerListMap.keySet().stream().map(films::get).collect(Collectors.toSet());
    }

    public Film saveFilm(Film film) {
        film.setId(countId++);
        films.put(film.getId(), film);
        log.info("Film Save " + film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ConstraintViolationException(Set.of());
        }
        Film film1 = films.get(film.getId());
        film.setId(film1.getId());
        films.put(film.getId(), film);
        log.info("Film Update " + film);
        return film;
    }

    public List<Film> getListFilm() {
        log.info("Get Films " + films.values());
        return new ArrayList<>(films.values());
    }

    public void clearFilms() {
        this.films.clear();
        countId = 0;
    }

    public static <K, V extends Collection> Map<K, V> sortMap(Map<K, V> map, Integer count) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .limit(count)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)
                );
    }
}
