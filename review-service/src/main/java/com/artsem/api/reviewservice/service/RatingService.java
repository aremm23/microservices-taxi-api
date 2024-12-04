package com.artsem.api.reviewservice.service;

import com.artsem.api.reviewservice.model.dto.response.RatingResponseDto;

public interface RatingService {

    RatingResponseDto getPassengerRating(Long passengerId);

    RatingResponseDto getDriverRating(Long driverId);

}
