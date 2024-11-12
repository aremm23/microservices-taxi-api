package com.artsem.api.rideservice.model.util;

import lombok.Builder;

@Builder
public record DistanceAndDurationValues(int distanceValue, int durationValue) {
}
