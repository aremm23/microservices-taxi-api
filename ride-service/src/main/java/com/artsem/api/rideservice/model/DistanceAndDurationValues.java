package com.artsem.api.rideservice.model;

import lombok.Builder;

@Builder
public record DistanceAndDurationValues(int distanceValue, int durationValue) {
}
