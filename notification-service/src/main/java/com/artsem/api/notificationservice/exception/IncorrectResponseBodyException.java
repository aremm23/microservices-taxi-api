package com.artsem.api.notificationservice.exception;

import com.artsem.api.notificationservice.util.ExceptionKeys;

public class IncorrectResponseBodyException extends RuntimeException {
    public IncorrectResponseBodyException() {
        super(ExceptionKeys.INCORRECT_RESPONSE_BODY);
    }
}
