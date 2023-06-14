package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long filmId;
}
