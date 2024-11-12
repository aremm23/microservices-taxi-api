package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException() {
        super(ExceptionKeys.RIDE_NOT_FOUND);
    }
}
