package com.artsem.api.rideservice.model;

public record RideCalculatePriceInfo(
        int distanceValue,
        int durationValue,
        RideTariff rideTariff
) {
}
