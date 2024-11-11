package com.example.demo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle the case for registering user with the same username
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Username already exists");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Handle database-related exceptions
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Database error");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle invalid input and request errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Invalid input");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle all other exceptions
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGeneralException(Throwable ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "An error occurred");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
