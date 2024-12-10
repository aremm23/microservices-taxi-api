package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;
import lombok.Getter;

@Getter
public class UnableParseJsonException extends RuntimeException {

    private final String jsonProcessingExceptionMessage;

    public UnableParseJsonException(String jsonProcessingExceptionMessage) {
        super(ExceptionKeys.JSON_PARSE_EXCEPTION);
        this.jsonProcessingExceptionMessage = jsonProcessingExceptionMessage;
    }
}