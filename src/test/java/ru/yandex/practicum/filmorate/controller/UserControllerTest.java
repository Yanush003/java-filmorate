package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        userService.clearUsers();
    }

    @Test
    void saveUser_success() throws Exception {
        User user1 = new User(null, "Alex", "123", "123@mail.com", LocalDate.of(1987, 12, 1));
        User user2 = new User(0, "Alex", "123", "123@mail.com", LocalDate.of(1987, 12, 1));

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(user1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user2)))
                .andExpect(jsonPath("$.id").value("0"));
    }

    @Test
    void updateUser_success() throws Exception {
        User user1 = new User(null, "Alex", "123", "123@mail.com", LocalDate.of(1987, 12, 1));
        User user2 = new User(0, "Alex2", "123", "123@mail.com", LocalDate.of(1987, 12, 1));
        userService.saveUser(user1);

        mockMvc.perform(
                        put("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user2)))
                .andExpect(jsonPath("$.id").value("0"))
                .andExpect(jsonPath("$.name").value("Alex2"));
    }

    @Test
    void getListUser_success() throws Exception {
        User user1 = new User(null, "Alex", "123", "123@mail.com", LocalDate.of(1987, 12, 1));
        User user2 = new User(0, "Alex", "123", "123@mail.com", LocalDate.of(1987, 12, 1));
        List<User> users = List.of(user1, user2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void saveNotValuedNameAndReleaseDateUser_thenStatus400anExceptionThrown() throws Exception {
        User user1 = new User(null, "Alex", "123", "123", LocalDate.of(1987, 12, 1));

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(user1)))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> Objects.requireNonNull(
                        mvcResult.getResolvedException()).getClass().equals(MethodArgumentNotValidException.class));
    }
}
