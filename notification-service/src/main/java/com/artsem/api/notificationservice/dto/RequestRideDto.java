package com.artsem.api.notificationservice.dto;

public record RequestRideDto(
        String passengerId,
        String rideId,
        String pickUpLocation,
        String dropOffLocation,
        String distance,
        String price,
        String tariff,
        String paymentMethod
) {
}
