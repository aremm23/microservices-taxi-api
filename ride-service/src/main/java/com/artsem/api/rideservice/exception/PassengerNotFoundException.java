package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException() {
        super(ExceptionKeys.PASSENGER_NOT_FOUND);
    }
}
