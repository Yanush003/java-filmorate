package ru.yandex.practicum.filmorate.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
@RequiredArgsConstructor
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerNotFoundException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NoSuchCustomerException.class)
    public ResponseEntity<?> handlerNotFoundException(NoSuchCustomerException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error", "404");
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerIllegalArgumentException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
