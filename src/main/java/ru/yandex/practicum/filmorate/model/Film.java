package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ValidDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
