package com.artsem.api.paymentservice.model.dto.response;

import lombok.Builder;

@Builder
public record CapturePaymentResponseDto(
        String sessionId,
        String sessionStatus,
        String paymentStatus
) {
}