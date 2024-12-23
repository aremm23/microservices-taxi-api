package com.artsem.api.reviewservice.service;

import com.artsem.api.reviewservice.feign.client.RideResponseDto;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;

public interface RideService {

    RideResponseDto getRideResponse(String rideId);

    void validateRide(RideResponseDto rideResponseDto, ReviewRequestDto reviewRequestDto);

}
