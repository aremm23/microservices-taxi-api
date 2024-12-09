package com.artsem.api.reviewservice.exceptions;


import com.artsem.api.reviewservice.util.ExceptionKeys;

public class InvalidRideForReviewException extends RuntimeException {
    public InvalidRideForReviewException() {
        super(ExceptionKeys.INVALID_RIDE_PROVIDED);
    }
}
