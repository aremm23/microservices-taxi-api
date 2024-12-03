package com.artsem.api.paymentservice.model.dto;

import lombok.Builder;

@Builder
public record IsBalancePositiveDto(boolean isBalancePositive, Long balanceUserId) {
}
