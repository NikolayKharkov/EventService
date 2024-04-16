package ru.kharkov.eventservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Ошибка валидации входных данных.")
                .detailMessage(e.getFieldErrors().get(0).getDefaultMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Ошибка валидации входных данных.")
                .detailMessage(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handle(NoSuchElementException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Сущность не найдена")
                .detailMessage(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Внутренняя ошибка сервера.")
                .detailMessage(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
