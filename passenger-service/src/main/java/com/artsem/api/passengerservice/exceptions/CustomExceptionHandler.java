package com.artsem.api.passengerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            PassengerNotCreatedException.class,
            PassengerNotFoundedException.class,
            IllegalArgumentException.class
    })
    private ResponseEntity<ErrorResponse> handlerException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}