package com.artsem.api.rideservice.feign;

import lombok.Builder;

@Builder
public record IsBalancePositiveResponse(boolean isBalancePositive, Long balanceUserId) {
}
