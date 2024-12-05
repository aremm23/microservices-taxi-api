package com.artsem.api.reviewservice.service.impl;

import com.artsem.api.reviewservice.exceptions.InvalidRideForReviewException;
import com.artsem.api.reviewservice.feign.client.RideResponseDto;
import com.artsem.api.reviewservice.feign.client.RideServiceClient;
import com.artsem.api.reviewservice.model.ReviewType;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private static final int FINISHED_STATUS_ID = 4;
    private static final int MAX_REVIEW_PERIOD_DAYS = 1;

    private final RideServiceClient rideServiceClient;

    @Override
    public RideResponseDto getRideResponse(String rideId) {
        return rideServiceClient.getRideById(rideId);
    }

    @Override
    public void validateRide(RideResponseDto rideResponse, ReviewRequestDto reviewRequest) {
        validateStatus(rideResponse);
        validateReviewPeriod(rideResponse);
        validateReviewMatch(rideResponse, reviewRequest);
    }

    private void validateStatus(RideResponseDto rideResponse) {
        if (rideResponse.statusId() != FINISHED_STATUS_ID) {
            throw new InvalidRideForReviewException();
        }
    }

    private void validateReviewPeriod(RideResponseDto rideResponse) {
        if (rideResponse.finishedTime().plusDays(MAX_REVIEW_PERIOD_DAYS).isBefore(LocalDateTime.now())) {
            throw new InvalidRideForReviewException();
        }
    }

    private void validateReviewMatch(RideResponseDto rideResponse, ReviewRequestDto reviewRequest) {
        if (reviewRequest.getReviewType() == ReviewType.DRIVER) {
            validateDriverReview(rideResponse, reviewRequest);
        } else if (reviewRequest.getReviewType() == ReviewType.PASSENGER) {
            validatePassengerReview(rideResponse, reviewRequest);
        } else {
            throw new InvalidRideForReviewException();
        }
    }

    private void validateDriverReview(RideResponseDto rideResponse, ReviewRequestDto reviewRequest) {
        if (!reviewRequest.getReviewedId().equals(rideResponse.driverId()) ||
                !reviewRequest.getReviewerId().equals(rideResponse.passengerId())) {
            throw new InvalidRideForReviewException();
        }
    }

    private void validatePassengerReview(RideResponseDto rideResponse, ReviewRequestDto reviewRequest) {
        if (!reviewRequest.getReviewedId().equals(rideResponse.passengerId()) ||
                !reviewRequest.getReviewerId().equals(rideResponse.driverId())) {
            throw new InvalidRideForReviewException();
        }
    }
}