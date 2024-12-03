package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;
import lombok.Getter;

@Getter
public class BalanceNotFoundException extends RuntimeException {
    private final long balanceId;

    public BalanceNotFoundException(long balanceId) {
        super(ExceptionKeys.BALANCE_NOT_FOUND);
        this.balanceId = balanceId;
    }
}
