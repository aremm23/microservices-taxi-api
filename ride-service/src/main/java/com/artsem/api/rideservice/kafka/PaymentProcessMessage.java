package com.artsem.api.rideservice.kafka;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentProcessMessage(
        Long userId,
        BigDecimal amount
) {
}