package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.annotation.ValidDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmRequestDto {
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @ValidDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    @NotNull
    private MpaDto mpa;

    private List<GenreDto> genres;


}
