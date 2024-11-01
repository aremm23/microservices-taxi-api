package com.artsem.api.passengerservice.exceptions;

import com.artsem.api.passengerservice.util.ExceptionKeys;

public class PassengerNotCreatedException extends RuntimeException {
    public PassengerNotCreatedException() {
        super(ExceptionKeys.PASSENGER_NOT_CREATED);
    }
}
