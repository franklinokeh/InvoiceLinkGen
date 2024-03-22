package com.ecotech.invoicelinkgen.controller;

import com.ecotech.invoicelinkgen.exceptions.AlreadyExistsException;
import com.ecotech.invoicelinkgen.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e) {
        return handle(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleConflictException( AlreadyExistsException e) {
        return handle(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e) {
        return handle(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleOtherException(RuntimeException e) {
        return handle(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handle(RuntimeException e, HttpStatus status) {
        log.error(e.getMessage(), e);
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", e.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
