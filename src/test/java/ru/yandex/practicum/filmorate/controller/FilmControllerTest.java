package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FilmService filmService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll() {

    }

    @BeforeEach
    public void clearList() {
        List<Film> listFilm = filmService.getListFilm();
        listFilm.clear();
    }

    @Test
    void saveFilm() throws Exception {
        Film film = new Film(null, "Titanic", "sdg", LocalDate.of(1954, 2, 1), 1);
        Film film1 = new Film(1, "Titanic", "sdg", LocalDate.of(1954, 2, 1), 1);

        mockMvc.perform(
                        post("/films")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(film)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(film1)))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void updateFilm() throws Exception {
        Film film = new Film(1, "Titanic1", "1", LocalDate.of(1954, 2, 1), 1);
        Film filmExpect = new Film(1, "Titanic2", "1", LocalDate.of(1954, 2, 1), 1);
        Film film1 = new Film(2, "Titanic2", "2", LocalDate.of(1954, 2, 1), 1);

        filmService.saveFilm(film);

        mockMvc.perform(
                        put("/films")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(film1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(filmExpect)))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Titanic2"));
    }

    @Test
    void getListFilm() throws Exception {

        Film film1 = new Film(0, "Titanic", "sdg", LocalDate.of(1954, 2, 1), 1);
        Film film2 = new Film(1, "Titanic", "sdg", LocalDate.of(1954, 2, 1), 1);
        Film film3 = new Film(2, "Titanic", "sdg", LocalDate.of(1954, 2, 1), 1);
        List<Film> films = List.of(film1, film2, film3);

        filmService.saveFilm(film1);
        filmService.saveFilm(film2);
        filmService.saveFilm(film3);

        mockMvc.perform(get("/films"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(films)));
    }

    @Test
    public void saveNotValuedNameAndReleaseDateFilm_thenStatus400anExceptionThrown() throws Exception {
        Film film = new Film(null, "", "sdg", LocalDate.of(1800, 2, 1), 1);

        mockMvc.perform(
                        post("/films")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(film)))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> Objects.requireNonNull(
                        mvcResult.getResolvedException()).getClass().equals(MethodArgumentNotValidException.class));
    }
}