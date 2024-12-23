package com.artsem.api.driverservice.model.dto.response;

import lombok.Builder;

@Builder
public record DriverStatusResponseDto(
        Boolean isFree
) {
}
