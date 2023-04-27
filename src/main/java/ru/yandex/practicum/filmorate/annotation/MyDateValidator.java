package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MyDateValidator implements ConstraintValidator<ValidDate, LocalDate> {
    public void initialize(ValidDate constraint) {
    }

    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDate.of(1895, 12, 28));
    }
}