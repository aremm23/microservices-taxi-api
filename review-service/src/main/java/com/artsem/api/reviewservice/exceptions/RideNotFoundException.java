package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.ExceptionKeys;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException() {
        super(ExceptionKeys.RIDE_NOT_FOUND);
    }
}
