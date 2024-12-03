package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;
import lombok.Getter;

@Getter
public class BalanceAlreadyExistsException extends RuntimeException {
    private final Long userId;

    public BalanceAlreadyExistsException(Long userId) {
        super(ExceptionKeys.BALANCE_ALREADY_EXISTS);
        this.userId = userId;
    }
}
