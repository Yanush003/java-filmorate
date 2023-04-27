package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "some message here";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class MyDateValidator implements ConstraintValidator<ValidDate, LocalDate> {
    public void initialize(ValidDate constraint) {
    }

    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDate.of(1895, 12, 28));
    }
}