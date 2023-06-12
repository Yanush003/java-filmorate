package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceIntegrationTest {

    private final FilmService filmService;

    @Test
    public void testGetFilmById() {
        // Создаем фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        Film film1 = filmService.saveFilm(film);

        // Получаем фильм по ID
        Film result = filmService.getFilmById(film1.getId());

        // Проверяем, что полученный фильм соответствует ожидаемому
        assertEquals(film1, result);
    }

    @Test
    public void testGetFilmByIdWithNullId() {
        // Пытаемся получить фильм с null ID
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> filmService.getFilmById(null));

        // Проверяем, что было выброшено исключение IllegalArgumentException
        assertEquals("ID must not be null", exception.getMessage());
    }

    @Test
    public void testPutLike() {
        // Создаем фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        Film film1 = filmService.saveFilm(film);

        // Добавляем лайк
        Long userId = 1L;
        filmService.putLike(film1.getId(), userId);

        // Проверяем, что лайк был добавлен
        Film result = filmService.getFilmById(film1.getId());
        assertTrue(result.getUserIds().contains(userId));
    }

    @Test
    public void testDeleteLike() {
        // Создаем фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        film = filmService.saveFilm(film);

        // Добавляем лайк
        Long userId = 1L;
        filmService.putLike(film.getId(), userId);

        // Удаляем лайк
        filmService.deleteLike(film.getId(), userId);

        // Проверяем, что лайк был удален
        Film result = filmService.getFilmById(film.getId());
        assertFalse(result.getUserIds().contains(userId));
    }

    @Test
    public void testGetFilms() {
        // Создаем несколько фильмов
        Film film1 = new Film();
        film1.setName("Film 1");
        film1.setDescription("Description 1");
        film1.setReleaseDate(LocalDate.of(2021, 1, 1));
        film1.setDuration(120);
        Film film1Result = filmService.saveFilm(film1);

        Film film2 = new Film();
        film2.setName("Film 2");
        film2.setDescription("Description 2");
        film2.setReleaseDate(LocalDate.of(2021, 2, 1));
        film2.setDuration(90);
        Film film2Result = filmService.saveFilm(film2);

        Film film3 = new Film();
        film3.setName("Film 3");
        film3.setDescription("Description 3");
        film3.setReleaseDate(LocalDate.of(2021, 3, 1));
        film3.setDuration(150);
        Film film3Result = filmService.saveFilm(film3);

        // Получаем топ-2 фильмов по лайкам
        List<Film> result = filmService.getFilms(2);

        // Проверяем, что полученные фильмы соответствуют ожидаемым
        assertEquals(2, result.size());
        assertEquals(film1Result, result.get(0));
        assertEquals(film2Result, result.get(1));
    }

    @Test
    public void testSaveFilm() {
        // Создаем фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);

        // Сохраняем фильм
        Film result = filmService.saveFilm(film);

        // Проверяем, что фильм был сохранен
        assertNotNull(result.getId());
        assertEquals(film.getName(), result.getName());
        assertEquals(film.getDescription(), result.getDescription());
        assertEquals(film.getReleaseDate(), result.getReleaseDate());
        assertEquals(film.getDuration(), result.getDuration());
    }

    @Test
    public void testUpdateFilm() {
        // Создаем фильм
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        film = filmService.saveFilm(film);

        // Обновляем фильм
        film.setName("Updated Film");
        film.setDescription("Updated Description");
        film.setReleaseDate(LocalDate.of(2021, 2, 1));
        film.setDuration(90);
        Film result = filmService.updateFilm(film);

        // Проверяем, что фильм был обновлен
        assertEquals(film.getId(), result.getId());
        assertEquals(film.getName(), result.getName());
        assertEquals(film.getDescription(), result.getDescription());
        assertEquals(film.getReleaseDate(), result.getReleaseDate());
        assertEquals(film.getDuration(), result.getDuration());
    }

    @Test
    public void testUpdateFilmWithNonexistentId() {
        // Создаем фильм
        Film film = new Film();
        film.setId(10L);
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2021, 1, 1));
        film.setDuration(120);
        // Пытаемся обновить несуществующий фильм
        Throwable exception = assertThrows(NotFoundException.class, () -> filmService.updateFilm(film));

        // Проверяем, что было выброшено исключение NotFoundException
        assertEquals("Film not found with ID: " + film.getId(), exception.getMessage());
    }
}