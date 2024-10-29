package com.artsem.api.passengerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            PassengerNotCreatedException.class,
            PassengerNotFoundedException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handlerException(RuntimeException e) {
        return new ResponseEntity<>(
                createErrorResponse(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                createErrorResponse(parseValidationExceptionInfo(ex).toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(
                message,
                LocalDateTime.now()
        );
    }

    private Map<String, String> parseValidationExceptionInfo(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}