package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;


@Repository
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION) values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());

        String sqlSelectFilm = "select * from FILMS WHERE name = ?";


        Film film1 = jdbcTemplate.queryForObject(sqlSelectFilm, new Object[]{film.getName()}, new FilmRowMapper());
        return film1;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update FILMS set NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? where ID = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());

        String sqlDeleteLike = "delete from likes where film_id = ?";
        jdbcTemplate.update(sqlDeleteLike, film.getId());

        String sqlAddLike = "insert into likes (film_id, user_id) values (?, ?)";
        for (Long userId : film.getUserIds()) {
            jdbcTemplate.update(sqlAddLike, film.getId(), userId);
        }
        String sqlSelectFilm = "select * from FILMS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sqlSelectFilm, new Object[]{film.getId()}, new FilmRowMapper());
    }

    @Override
    public Film get(Long id) {
        String sqlQuery = "select * from FILMS where ID = ?";

        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new FilmRowMapper());
        } catch (Exception e) {
            throw new NotFoundException("ID cannot be found");
        }
        return film;
    }

    @Override
    public void delete(Long id) {
        String sqlQuery = "delete from FILMS where ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "select * from FILMS";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper());
    }

}