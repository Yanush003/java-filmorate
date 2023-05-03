package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    private String name;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;
    @Email(message = "Введенный email не соответствует формату email-адреса")
    private String email;
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public User(Integer id, String name, String login, String email, LocalDate birthday) {
        this.id = id;
        this.name = (name.isEmpty()) ? login : name;
        this.login = login.replace(" ", "");
        this.email = email;
        this.birthday = birthday;
    }

    public User(Integer id, String login, String email, LocalDate birthday) {
        this.id = id;
        this.login = login.replace(" ", "");
        this.email = email;
        this.birthday = birthday;
    }
}
