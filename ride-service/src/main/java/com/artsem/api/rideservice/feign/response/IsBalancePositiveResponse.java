package com.artsem.api.rideservice.feign.response;

import lombok.Builder;

@Builder
public record IsBalancePositiveResponse(
        boolean isBalancePositive,
        Long balanceUserId
) {
}
