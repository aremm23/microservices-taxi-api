package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.model.dto.internal.AcceptedRideDto;
import com.artsem.api.rideservice.model.dto.internal.CancelledRideDto;
import com.artsem.api.rideservice.model.dto.internal.CompletedRideDto;
import com.artsem.api.rideservice.model.dto.internal.RequestedRideDto;
import com.artsem.api.rideservice.model.dto.internal.StartedRideDto;

public interface RideLifecycleService {

    void requestRide(RequestedRideDto rideDto);

    void acceptRide(AcceptedRideDto rideDto);

    void startRide(StartedRideDto rideDto);

    void finishRide(CompletedRideDto rideDto);

    void cancelRide(CancelledRideDto rideDto);

}