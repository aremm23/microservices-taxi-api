package com.artsem.api.notificationservice.dto;

import lombok.Builder;

@Builder
public record AcceptedRideDto(
        String driverId,
        String passengerId,
        String rideId,
        String carLicensePlate
) {
}
