package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException() {
        super(ExceptionKeys.DRIVER_NOT_FOUND);
    }
}
