package com.artsem.api.reviewservice.feign.client;

import com.artsem.api.reviewservice.util.PatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RideResponseDto(
        String id,
        Long passengerId,
        String pickUpLocation,
        String dropOffLocation,
        Long driverId,
        Long carId,
        String carLicensePlate,
        Double distance,
        Double price,
        Integer tariffId,
        Integer paymentMethodId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime requestedTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime acceptedTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime startedTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime finishedTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime canceledTime,
        Integer statusId
) {
}