package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.ValidDate;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private Integer id;
    @NotEmpty
    private String name;
    @NotBlank
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
