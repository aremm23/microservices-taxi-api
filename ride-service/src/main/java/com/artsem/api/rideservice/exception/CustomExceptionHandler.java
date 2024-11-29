package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ValidationKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            RideNotFoundException.class,
            InvalidRideStatusException.class,
            InvalidResponseElementException.class,
            InvalidResponseException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handlerException(RuntimeException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(
                createErrorResponse(message),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveValidationExceptionInfo(ex).toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(
                message,
                LocalDateTime.now()
        );
    }

    private Map<String, String> resolveValidationExceptionInfo(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessageKey = getValidationErrorMessageKey(error);
            String errorMessage = messageSource.getMessage(
                    errorMessageKey,
                    null,
                    LocaleContextHolder.getLocale()
            );
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private String getValidationErrorMessageKey(ObjectError error) {
        String errorMessageKey = error.getDefaultMessage();
        if (errorMessageKey == null) {
            errorMessageKey = ValidationKeys.UNKNOWN_MESSAGE;
        }
        return errorMessageKey;
    }

}