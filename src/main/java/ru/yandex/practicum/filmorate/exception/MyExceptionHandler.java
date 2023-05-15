package ru.yandex.practicum.filmorate.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@RestControllerAdvice
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerIllegalArgumentException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerNotFoundException(NoSuchCustomerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }
}
