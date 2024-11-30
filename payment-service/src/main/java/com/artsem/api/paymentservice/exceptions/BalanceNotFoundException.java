package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException() {
        super(ExceptionKeys.BALANCE_NOT_FOUND);
    }
}
