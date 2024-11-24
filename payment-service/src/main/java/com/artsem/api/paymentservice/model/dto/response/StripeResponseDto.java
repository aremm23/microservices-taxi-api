package com.artsem.api.paymentservice.model.dto.response;

import lombok.Builder;

@Builder
public record StripeResponseDto(
        String status,
        String sessionId,
        String sessionUrl
) {
}
