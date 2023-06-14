package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dto.GenreDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDtoMapper implements RowMapper<GenreDto> {

    @Override
    public GenreDto mapRow(ResultSet resultSet, int i) throws SQLException {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(resultSet.getLong("ID"));
        genreDto.setName(resultSet.getString("NAME"));

        return genreDto;
    }
}
