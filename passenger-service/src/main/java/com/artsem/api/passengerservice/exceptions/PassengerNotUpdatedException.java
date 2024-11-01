package com.artsem.api.passengerservice.exceptions;

import com.artsem.api.passengerservice.util.ExceptionKeys;

public class PassengerNotUpdatedException extends RuntimeException {
    public PassengerNotUpdatedException() {
        super(ExceptionKeys.PASSENGER_NOT_UPDATED);
    }
}
