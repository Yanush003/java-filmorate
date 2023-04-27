package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ValidDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @ValidDate
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
}
