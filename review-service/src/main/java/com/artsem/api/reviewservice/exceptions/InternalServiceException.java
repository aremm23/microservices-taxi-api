package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.ExceptionKeys;
import lombok.Getter;

@Getter
public class InternalServiceException extends RuntimeException {
    private final String message;

    public InternalServiceException(String message) {
        super(ExceptionKeys.INTERNAL_EXCEPTION);
        this.message = message;
    }
}
