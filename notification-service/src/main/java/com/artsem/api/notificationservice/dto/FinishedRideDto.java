package com.artsem.api.notificationservice.dto;

public record FinishedRideDto(
        String rideId,
        String driverId,
        String passengerId
) {
}
