package com.artsem.api.paymentservice.model.dto.request;

import lombok.Builder;

@Builder
public record InitBalanceRequestDto(
        Long userId
) {
}
