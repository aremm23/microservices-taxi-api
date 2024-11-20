package com.artsem.api.notificationservice.dto;

public record AcceptedRideDto(
        String driverId,
        String passengerId,
        String rideId,
        String carLicensePlate
) {
}
