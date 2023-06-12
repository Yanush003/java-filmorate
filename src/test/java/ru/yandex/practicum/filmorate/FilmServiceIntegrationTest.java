package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceIntegrationTest {

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmDbStorage filmDbStorage;

    @Autowired
    private UserService userService;

    @Test
    public void testGetFilmById() {
        // Создаем фильм в базе данных
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        Film film1 = filmDbStorage.create(film);

        // Получаем фильм по ID
        Film result = filmService.getFilmById(film1.getId());

        // Проверяем, что полученный фильм соответствует ожидаемому
        assertEquals(film1, result);
    }

    @Test
    public void testPutLike() {
        // Создаем фильм в базе данных
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        Film film1 = filmDbStorage.create(film);

        // Создаем пользователя в базе данных
        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@example.com");
        user.setBirthday(LocalDate.now());
        User user1 = userService.saveUser(user);

        // Ставим лайк на фильм от пользователя
        filmService.putLike(film1.getId(), user1.getId());
    }

    @Test
    public void testDeleteLike() {
        // Создаем фильм в базе данных
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        Film film1 = filmDbStorage.create(film);

        // Создаем пользователя в базе данных
        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@example.com");
        user.setBirthday(LocalDate.now());
        User user1 = userService.saveUser(user);

        // Ставим лайк на фильм от пользователя
        filmService.putLike(film1.getId(), user1.getId());

        // Удаляем лайк на фильм от пользователя
        filmService.deleteLike(film1.getId(), user1.getId());

        // Получаем обновленный фильм из базы данных
        Film updatedFilm = filmDbStorage.get(film1.getId());

        // Проверяем, что пользователь удален из списка лайков фильма
        assertFalse(updatedFilm.getUserIds().contains(user1.getId()));
    }

    @Test
    public void testGetFilms() {
        // Создаем несколько фильмов в базе данных
        Film film1 = new Film();
        film1.setName("Test Film 1");
        film1.setDescription("Test Description 1");
        film1.setReleaseDate(LocalDate.now());
        film1.setDuration(120);
        Film film = filmDbStorage.create(film1);

        Film film2 = new Film();
        film2.setName("Test Film 2");
        film2.setDescription("Test Description 2");
        film2.setReleaseDate(LocalDate.now());
        film2.setDuration(90);
        Film film3 = filmDbStorage.create(film2);

        // Создаем несколько пользователей в базе данных
        User user1 = new User();
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@example.com");
        user1.setBirthday(LocalDate.now());
        User user = userService.saveUser(user1);

        User user2 = new User();
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@example.com");
        user2.setBirthday(LocalDate.now());
        User user3 = userService.saveUser(user2);

        // Ставим лайки на фильмы от пользователей
        filmService.putLike(film.getId(), user.getId());
        filmService.putLike(film.getId(), user3.getId());
        filmService.putLike(film3.getId(), user.getId());

        // Получаем список фильмов, отсортированных по количеству лайков
        List<Film> films = filmService.getFilms(2);

        // Провер, что полученный список фильмов соответствует ожидаемому
        assertEquals(2, films.size());
    }

    @Test
    public void testSaveFilm() {
        // Создаем фильм для сохранения
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);

        // Сохраняем фильм в базе данных
        Film savedFilm = filmService.saveFilm(film);

        // Получаем фильм из базы данных
        Film result = filmDbStorage.get(savedFilm.getId());

        // Проверяем, что полученный фильм соответствует ожидаемому
        assertEquals(savedFilm, result);
    }

    @Test
    public void testUpdateFilm() {
        // Создаем фильм для обновления
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        Film film1 = filmDbStorage.create(film);

        // Обновляем фильм в базе данных
        film.setName("Updated Test Film");
        Film updatedFilm = filmService.updateFilm(film1);

        // Получаем обновленный фильм из базы данных
        Film result = filmDbStorage.get(updatedFilm.getId());

        // Проверяем, что полученный фильм соответствует ожидаемому
        assertEquals(film1, result);
    }
}

