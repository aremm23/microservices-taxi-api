package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ValidationKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(UnableVerifyWebhookSignatureException.class)
    public ResponseEntity<ErrorResponse> handlerException(RuntimeException e) {
        String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(
                createErrorResponse(message),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveValidationExceptionInfo(ex).toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BalanceNotFoundByUserIdException.class)
    public ResponseEntity<ErrorResponse> handleBalanceNotFoundByUserIdExceptions(BalanceNotFoundByUserIdException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveBalanceNotFoundByUserIdExceptionInfo(ex)),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BalanceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBalanceAlreadyExistsExceptions(BalanceAlreadyExistsException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveBalanceAlreadyExistsExceptionInfo(ex)),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BalanceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBalanceNotFoundExceptions(BalanceNotFoundException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveBalanceNotFoundExceptionInfo(ex)),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnableParseJsonException.class)
    public ResponseEntity<ErrorResponse> handleUnableParseJsonExceptions(UnableParseJsonException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveUnableParseJsonExceptionInfo(ex)),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(StripeSessionException.class)
    public ResponseEntity<ErrorResponse> handleStripeSessionExceptions(StripeSessionException ex) {
        return new ResponseEntity<>(
                createErrorResponse(resolveStripeSessionExceptionInfo(ex)),
                HttpStatus.BAD_REQUEST
        );
    }

    private String resolveBalanceAlreadyExistsExceptionInfo(BalanceAlreadyExistsException ex) {
        return messageSource.getMessage(
                Objects.requireNonNull(ex.getMessage()),
                new Object[]{ex.getUserId()},
                LocaleContextHolder.getLocale()
        );
    }

    private String resolveBalanceNotFoundByUserIdExceptionInfo(BalanceNotFoundByUserIdException ex) {
        return messageSource.getMessage(
                Objects.requireNonNull(ex.getMessage()),
                new Object[]{ex.getUserId()},
                LocaleContextHolder.getLocale()
        );
    }

    private String resolveBalanceNotFoundExceptionInfo(BalanceNotFoundException ex) {
        return messageSource.getMessage(
                Objects.requireNonNull(ex.getMessage()),
                new Object[]{ex.getBalanceId()},
                LocaleContextHolder.getLocale()
        );
    }

    private String resolveUnableParseJsonExceptionInfo(UnableParseJsonException ex) {
        return messageSource.getMessage(
                Objects.requireNonNull(ex.getMessage()),
                new Object[]{ex.getJsonProcessingExceptionMessage()},
                LocaleContextHolder.getLocale()
        );
    }

    private String resolveStripeSessionExceptionInfo(StripeSessionException ex) {
        return messageSource.getMessage(
                Objects.requireNonNull(ex.getMessage()),
                new Object[]{ex.getStripeExceptionMessage()},
                LocaleContextHolder.getLocale()
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