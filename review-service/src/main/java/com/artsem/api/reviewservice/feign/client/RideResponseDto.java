package com.artsem.api.reviewservice.feign.client;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime requestedTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime acceptedTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startedTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime finishedTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime canceledTime,
    Integer statusId
) {}