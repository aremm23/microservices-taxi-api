package com.artsem.api.notificationservice.dto;

import lombok.Builder;

@Builder
public record FinishedRideDto(
        String rideId,
        String driverId,
        String passengerId
) {
}
