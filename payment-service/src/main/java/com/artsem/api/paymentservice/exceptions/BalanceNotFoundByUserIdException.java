package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;
import lombok.Getter;

@Getter
public class BalanceNotFoundByUserIdException extends RuntimeException {
    private final long userId;

    public BalanceNotFoundByUserIdException(long userId) {
        super(ExceptionKeys.BALANCE_NOT_FOUND);
        this.userId = userId;
    }
}
