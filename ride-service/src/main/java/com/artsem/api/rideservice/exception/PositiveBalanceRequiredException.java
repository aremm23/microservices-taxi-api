package com.artsem.api.rideservice.exception;

import com.artsem.api.rideservice.util.ExceptionKeys;

public class PositiveBalanceRequiredException extends RuntimeException{
    public PositiveBalanceRequiredException() {
        super(ExceptionKeys.POSITIVE_BALANCE_REQUIRED);
    }
}
