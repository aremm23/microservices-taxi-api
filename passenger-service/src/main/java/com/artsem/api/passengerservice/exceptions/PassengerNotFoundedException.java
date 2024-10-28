package com.artsem.api.passengerservice.exceptions;

public class PassengerNotFoundedException extends RuntimeException {
    public PassengerNotFoundedException(String message) {
        super(message);
    }
}
