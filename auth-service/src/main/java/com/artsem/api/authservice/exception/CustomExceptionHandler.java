package com.artsem.api.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            ResponseStatusException.class
    })
    private ResponseEntity<ErrorResponse> handlerException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Keycloak response status: %s".formatted(e.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}