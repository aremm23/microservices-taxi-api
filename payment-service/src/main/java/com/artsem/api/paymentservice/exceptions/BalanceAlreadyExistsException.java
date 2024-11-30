package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;

public class BalanceAlreadyExistsException extends RuntimeException {
    public BalanceAlreadyExistsException() {
        super(ExceptionKeys.BALANCE_ALREADY_EXISTS);
    }
}
