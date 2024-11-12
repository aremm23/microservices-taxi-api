package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class InvalidResponseElementException extends RuntimeException{
    public InvalidResponseElementException() {
        super(ExceptionKeys.INVALID_API_ELEMENT_RESPONSE);
    }
}
