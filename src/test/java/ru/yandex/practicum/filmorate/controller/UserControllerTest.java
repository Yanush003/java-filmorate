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
        List<User> userList = userService.getUserList();
        userList.clear();
    }

    @Test
    void saveUser_success() throws Exception {
        User user1 = User.builder()
                .id(null)
                .name("Alex")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();
        User user2 = User.builder()
                .id(1)
                .name("Alex")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();

        mockMvc.perform(
                        post("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(user1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user2)))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void updateUser_success() throws Exception {
        User user1 = User.builder()
                .id(null)
                .name("Alex1")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();
        User user2 = User.builder()
                .id(1)
                .name("Alex2")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();
        userService.createUser(user1);

        mockMvc.perform(
                        put("/api/user/update")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user2)))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Alex2"));
    }

    @Test
    void getListUser_success() throws Exception {

        User user1 = User.builder()
                .id(null)
                .name("Alex")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();
        User user2 = User.builder()
                .id(1)
                .name("Alex")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("123")
                .build();
        List<User> users = List.of(user1, user2);

        userService.createUser(user1);
        userService.createUser(user2);

        mockMvc.perform(get("/api/user/get"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void saveNotValuedNameAndReleaseDateUser_thenStatus400anExceptionThrown() throws Exception {
        User user1 = User.builder()
                .id(null)
                .name("")
                .birthday(LocalDate.of(1987, 12, 1))
                .email("123@mail.com")
                .login("")
                .build();

        mockMvc.perform(
                        post("/api/user/create")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(user1)))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> Objects.requireNonNull(
                        mvcResult.getResolvedException()).getClass().equals(MethodArgumentNotValidException.class));
    }
}
