package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.model.util.DistanceAndDurationValues;

public interface RideDistanceService {

    DistanceAndDurationValues getDistanceAndDuration(String pickUpLocation, String dropOffLocation);

}