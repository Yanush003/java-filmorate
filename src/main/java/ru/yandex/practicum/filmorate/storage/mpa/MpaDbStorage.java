package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa create(Mpa mpa) {
        return null;
    }

    @Override
    public void updateMpa(Long idFilm, Long idMpa) {
        String sqlQuery = "update FILM_RATINGS set rating_id = ? where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, idMpa, idFilm);
    }

    @Override
    public Mpa getMpa(Long filmId) {
        String sqlQuery = "select r.id, r.name\n" +
                "from FILMS as f\n" +
                "join FILM_RATINGS as fr on f.ID = fr.FILM_ID\n" +
                "join RATINGS as r on fr.RATING_ID = r.ID\n" +
                "where f.ID = ?;";
        Mpa mpa;
        try {
            mpa = jdbcTemplate.queryForObject(sqlQuery, new Object[]{filmId}, new MpaMapper());
        } catch (Exception e) {
            throw new NotFoundException("ID cannot be found");
        }
        return mpa;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "select * from RATINGS ";
        return jdbcTemplate.query(sqlQuery, new MpaMapper());
    }

    public void setMpa(Long idFilm, Long idMpa) {
        String sqlQuery = "insert into FILM_RATINGS (film_id, rating_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, idFilm, idMpa);
    }

    public Mpa getMpaById(Long id) {
        String sqlSelectFilm = "select * from RATINGS WHERE id = ?";
        Mpa mpa;
        try {
            mpa = jdbcTemplate.queryForObject(sqlSelectFilm, new Object[]{id}, new MpaMapper());
        } catch (Exception e) {
            throw new NotFoundException("ID cannot be found");
        }
        if (mpa == null) throw new NotFoundException("cannot be found");
        return mpa;
    }
}
