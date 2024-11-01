package com.artsem.api.passengerservice.exceptions;

import com.artsem.api.passengerservice.util.ExceptionKeys;

public class PassengersNotFoundException extends RuntimeException {
    public PassengersNotFoundException() {
        super(ExceptionKeys.PASSENGERS_NOT_FOUND);
    }
}
