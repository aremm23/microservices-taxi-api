package com.artsem.api.rideservice.model.util;

import com.artsem.api.rideservice.model.RideTariff;

public record RideCalculatePriceInfo(
        int distanceValue,
        int durationValue,
        RideTariff rideTariff
) {
}
