package com.artsem.api.driverservice.model.dto.request;

import lombok.Builder;

@Builder
public record DriverStatusUpdateRequestDto(Boolean isFree) {
}