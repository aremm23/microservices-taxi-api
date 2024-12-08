package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class InternalServiceException extends RuntimeException {
    public InternalServiceException() {
        super(ExceptionKeys.INTERNAL_SERVER_EXCEPTION);
    }
}
