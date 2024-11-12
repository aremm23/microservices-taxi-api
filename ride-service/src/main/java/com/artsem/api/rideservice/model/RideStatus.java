package com.artsem.api.rideservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RideStatus {
    REQUESTED(1),
    ACCEPTED(2),
    STARTED(3),
    FINISHED(4),
    CANCELLED_BY_DRIVER(5),
    CANCELLED_BY_PASSENGER(6);

    private final int id;

    public static RideStatus fromId(int id) {
        for (RideStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid id for RideStatus: " + id);
    }
}