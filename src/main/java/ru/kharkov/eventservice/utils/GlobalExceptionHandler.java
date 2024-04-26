package ru.kharkov.eventservice.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        String errorMessage =
                e instanceof MethodArgumentNotValidException ?
                        ((MethodArgumentNotValidException) e).getFieldErrors().get(0).getDefaultMessage() :
                        e.getMessage();
        LOG.debug(String.format("Ошибка при валидации данных. Ошибка сообщения: %s", errorMessage));
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Ошибка валидации входных данных.")
                .detailMessage(errorMessage)
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handle(NoSuchElementException e) {
        String errorMessage = e.getMessage();
        LOG.debug(String.format("Ошибка при поиске сущности. Ошибка сообщения: %s", errorMessage));
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Сущность не найдена")
                .detailMessage(errorMessage)
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException e) {
        String errorMessage = e.getMessage();
        LOG.debug(String.format("Ошибка сервиса. Ошибка сообщения: %s", errorMessage));
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message("Внутренняя ошибка сервера.")
                .detailMessage(errorMessage)
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
