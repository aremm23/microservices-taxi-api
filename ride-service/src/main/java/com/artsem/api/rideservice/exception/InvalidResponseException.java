package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class InvalidResponseException extends RuntimeException{
    public InvalidResponseException() {
        super(ExceptionKeys.INVALID_API_RESPONSE);
    }
}
