package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
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
