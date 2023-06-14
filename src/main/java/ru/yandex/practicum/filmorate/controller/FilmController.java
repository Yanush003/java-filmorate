package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final MpaService mpaService;
    private final GenreService genreService;


    @GetMapping("/{id}")
    public FilmResponseDto getFilmById(@PathVariable Long id) {
        return mapToResponse(filmService.getFilmById(id), mpaService.getMpa(id), genreService.getGenre(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void putLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmResponseDto> getFilms(@RequestParam(required = false, name = "count") Integer count) {
        if (count == null) {
            count = 10;
        }
        List<FilmResponseDto> filmResponseDtos = new ArrayList<>();
        List<Film> films = filmService.getFilms(count);
        for (Film film : films) {
            FilmResponseDto filmResponseDto = mapToResponse(film, mpaService.getMpa(film.getId()), genreService.getGenre(film.getId()));
            filmResponseDtos.add(filmResponseDto);
        }
        return filmResponseDtos;
    }

    @GetMapping
    public List<FilmResponseDto> getAll() {

        List<Film> all = filmService.getAll();
        List<FilmResponseDto> filmResponseDtos = new ArrayList<>();
        for (Film film : all) {
            FilmResponseDto filmResponseDto = mapToResponse(film, mpaService.getMpa(film.getId()), genreService.getGenre(film.getId()));
            filmResponseDtos.add(filmResponseDto);
        }
        return filmResponseDtos;
    }

    @PostMapping
    public FilmResponseDto saveFilm(@Valid @RequestBody FilmRequestDto filmDto) {
        Film film = new Film(null, filmDto.getName(), filmDto.getDescription(), filmDto.getReleaseDate(), filmDto.getDuration(), new HashSet<>());
        Film saveFilm = filmService.saveFilm(film);
        mpaService.setMpa(saveFilm.getId(), filmDto.getMpa().getId());
        if (filmDto.getGenres() != null) {
            List<Long> genreIds = new ArrayList<>();

            for (GenreDto genre : filmDto.getGenres()) {
                genreIds.add(genre.getId());
            }
            genreService.addGenres(saveFilm.getId(), genreIds);
        }
        Mpa mpa = mpaService.getMpa(saveFilm.getId());
        List<GenreDto> genre = genreService.getGenre(saveFilm.getId());
        return mapToResponse(saveFilm, mpa, genre);
    }

    @PutMapping
    public FilmResponseDto updateFilm(@Valid @RequestBody FilmRequestDto filmDto) {
        Film film = new Film(filmDto.getId(), filmDto.getName(), filmDto.getDescription(), filmDto.getReleaseDate(), filmDto.getDuration(), new HashSet<>());
        Film updatedFilm = filmService.updateFilm(film);
        mpaService.updateMpa(filmDto.getId(), filmDto.getMpa().getId());
        if (filmDto.getGenres() != null) {
            genreService.updateGenre(filmDto.getGenres(), film.getId());
        }
        Mpa mpa = mpaService.getMpa(filmDto.getId());
        List<GenreDto> genre = genreService.getGenre(filmDto.getId());
        return mapToResponse(updatedFilm, mpa, genre);
    }

    private static FilmResponseDto mapToResponse(Film film, Mpa mpa, List<GenreDto> genres) {
        Set<GenreDto> set = new HashSet<>(genres);
        genres.clear();
        genres.addAll(set);
        MpaDto mpaDto = mapToDto(mpa);
        return new FilmResponseDto(film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), mpaDto, genres);
    }

    private static MpaDto mapToDto(Mpa mpa) {
        if (mpa != null) {
            return new MpaDto(mpa.getId(), mpa.getName());
        }
        return null;
    }
}
