package ru.yandex.practicum.filmorate.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilmServiceUnitTest {

    @Mock
    private FilmDbStorage storage;

    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filmService = new FilmService(storage);
    }

    @Test
    void testGetFilmById() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        Set<Long> userIds = new HashSet<>();
        userIds.add(1L);
        userIds.add(2L);
        film.setUserIds(userIds);

        when(storage.get(1L)).thenReturn(film);

        Film result = filmService.getFilmById(1L);

        assertEquals(film, result);
        verify(storage, times(1)).get(1L);
    }

    @Test
    void testPutLike() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        Set<Long> userIds = new HashSet<>();
        userIds.add(1L);
        film.setUserIds(userIds);

        when(storage.get(1L)).thenReturn(film);

        filmService.putLike(1L, 2L);

        assertTrue(film.getUserIds().contains(2L));
        verify(storage, times(1)).update(film);
    }

    @Test
    void testDeleteLike() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        Set<Long> userIds = new HashSet<>();
        userIds.add(1L);
        userIds.add(2L);
        film.setUserIds(userIds);

        when(storage.get(1L)).thenReturn(film);

        filmService.deleteLike(1L, 2L);

        assertFalse(film.getUserIds().contains(2L));
        verify(storage, times(1)).create(film);
    }

    @Test
    void testGetFilms() {
        Film film1 = new Film();
        film1.setId(1L);
        film1.setName("Test Film 1");
        film1.setDescription("This is a test film 1");
        film1.setReleaseDate(LocalDate.of(2021, 1, 1));
        film1.setDuration(120);
        Set<Long> userIds1 = new HashSet<>();
        userIds1.add(1L);
        userIds1.add(2L);
        film1.setUserIds(userIds1);

        Film film2 = new Film();
        film2.setId(2L);
        film2.setName("Test Film 2");
        film2.setDescription("This is a test film 2");
        film2.setReleaseDate(LocalDate.of(2021, 1, 1));
        film2.setDuration(120);
        Set<Long> userIds2 = new HashSet<>();
        userIds2.add(1L);
        film2.setUserIds(userIds2);

        List<Film> films = new ArrayList<>();
        films.add(film1);
        films.add(film2);

        when(storage.getAll()).thenReturn(films);

        List<Film> result = filmService.getFilms(1);

        assertEquals(1, result.size());
        assertEquals(film1, result.get(0));
    }

    @Test
    void testSaveFilm() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);

        when(storage.create(film)).thenReturn(film);

        Film result = filmService.saveFilm(film);

        assertEquals(film, result);
        verify(storage, times(1)).create(film);
    }

    @Test
    void testUpdateFilm() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setDescription("This is a test film");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);

        Film oldFilm = new Film();
        oldFilm.setId(1L);
        oldFilm.setName("Old Test Film");
        oldFilm.setDescription("This is an old test film");
        oldFilm.setReleaseDate(LocalDate.of(2020, 1, 1));
        oldFilm.setDuration(90);

        when(storage.get(1L)).thenReturn(oldFilm);
        when(storage.create(film)).thenReturn(film);

        Film result = filmService.updateFilm(film);

        assertEquals(film, result);

        verify(storage, times(1)).create(film);
    }
}


