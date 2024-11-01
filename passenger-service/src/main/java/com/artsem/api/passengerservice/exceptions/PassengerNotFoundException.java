package com.artsem.api.passengerservice.exceptions;

import com.artsem.api.passengerservice.util.ExceptionKeys;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException() {
        super(ExceptionKeys.PASSENGER_NOT_FOUND);
    }
}
