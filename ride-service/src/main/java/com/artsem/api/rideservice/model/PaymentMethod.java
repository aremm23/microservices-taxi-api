package com.artsem.api.rideservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CASH(1),
    BALANCE(2);

    private final int id;

    public static PaymentMethod fromId(int id) {
        for (PaymentMethod method : values()) {
            if (method.getId() == id) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid id for PaymentMethod: " + id);
    }
}