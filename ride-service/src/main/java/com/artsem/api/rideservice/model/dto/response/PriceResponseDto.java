package com.artsem.api.rideservice.model.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceResponseDto(
        BigDecimal price
) {
}
