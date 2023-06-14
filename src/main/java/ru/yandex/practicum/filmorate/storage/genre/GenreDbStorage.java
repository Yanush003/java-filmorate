package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreDtoMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Repository
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public List<GenreDto> getGenre(Long filmId) {
        String sqlQuery = "select g.id, g.name\n" +
                "from FILMS as f\n" +
                "join FILM_GENRES as fg on f.ID = fg.FILM_ID\n" +
                "join GENRES as g on fg.GENRE_ID = g.ID\n" +
                "where f.ID = ?;";
        List<GenreDto> genre;
        try {
            genre = jdbcTemplate.query(sqlQuery, new Object[]{filmId}, new GenreDtoMapper());
        } catch (Exception e) {
            throw new NotFoundException("ID cannot be found");
        }
        return genre;
    }

    @Override
    public void delete(Long id) {
    }

    public void updateGenre(List<GenreDto> genreDtos, Long filmId) {
        String sqlQuery1 = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(sqlQuery1, filmId);
        String sqlQuery = "insert into film_genres (film_id, genre_id) values (?, ?)";
        for (GenreDto genreDto:genreDtos) {
            jdbcTemplate.update(sqlQuery, filmId, genreDto.getId());
        }
    }

    public Genre getById(Long id) {
        String sqlQuery = "select* from genres where id = ?;";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new GenreMapper(), id);
        } catch (Exception e) {
            throw new NotFoundException("genre cannot be found");
        }
    }

    @Override
    public List<Genre> getGenres() {
        String sqlQuery = "select * from GENRES ";
        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }

    public void addGenres(Long filmId, List<Long> genreIds) {
        String sqlQuery = "insert into FILM_GENRES (FILM_ID, GENRE_ID) values (?, ?)";
        for (Long genreId : genreIds) {
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        }
    }
}
