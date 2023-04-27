package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "some message here";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
