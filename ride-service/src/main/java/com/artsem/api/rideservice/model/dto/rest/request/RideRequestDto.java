package com.artsem.api.rideservice.model.dto.rest.request;

import com.artsem.api.rideservice.util.ValidationKeys;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RideRequestDto {

    @NotNull(message = ValidationKeys.PASSENGER_ID_REQUIRED)
    private Long passengerId;

    @NotBlank(message = ValidationKeys.PICKUP_LOCATION_REQUIRED)
    private String pickUpLocation;

    @NotBlank(message = ValidationKeys.DROP_OFF_LOCATION_REQUIRED)
    private String dropOffLocation;

    @NotNull(message = ValidationKeys.DRIVER_ID_REQUIRED)
    private Long driverId;

    @NotNull(message = ValidationKeys.CAR_ID_REQUIRED)
    private Long carId;

    @NotBlank(message = ValidationKeys.CAR_LICENSE_PLATE_REQUIRED)
    private String carLicensePlate;

    @NotNull(message = ValidationKeys.DISTANCE_REQUIRED)
    @Positive(message = ValidationKeys.DISTANCE_POSITIVE)
    private Double distance;

    @NotNull(message = ValidationKeys.PRICE_REQUIRED)
    @PositiveOrZero(message = ValidationKeys.PRICE_POSITIVE_OR_ZERO)
    private Double price;

    @NotNull(message = ValidationKeys.TARIFF_ID_REQUIRED)
    private Long tariffId;

    @NotNull(message = ValidationKeys.PAYMENT_METHOD_ID_REQUIRED)
    private Long paymentMethodId;

    @NotNull(message = ValidationKeys.REQUESTED_TIME_REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestedTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acceptedTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime canceledTime;

    @NotNull(message = ValidationKeys.STATUS_ID_REQUIRED)
    private Long statusId;
}