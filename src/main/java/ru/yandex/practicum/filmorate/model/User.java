package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @NotEmpty
    private String name;
    @NotBlank
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;
    @Email
    private String email;
    @Past
    private LocalDate birthday;

    public User(Integer id, String name, String login, String email, LocalDate birthday) {
        this.id = id;
        this.name = (name.isEmpty()) ? login : name;
        this.login = login.replace(" ", "");
        this.email = email;
        this.birthday = birthday;
    }
}
