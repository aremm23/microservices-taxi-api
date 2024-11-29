package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.model.dto.request.AcceptedRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.CancelledRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.RequestedRideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;

public interface RideLifecycleService {

    RideResponseDto requestRide(RequestedRideRequestDto rideDto);

    RideResponseDto acceptRide(String rideId, AcceptedRideRequestDto rideDto);

    RideResponseDto startRide(String rideId);

    RideResponseDto finishRide(String rideId);

    RideResponseDto cancelRide(String rideId, CancelledRideRequestDto rideDto);

}