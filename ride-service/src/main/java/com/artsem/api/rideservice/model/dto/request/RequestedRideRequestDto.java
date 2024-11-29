package com.artsem.api.rideservice.model.dto.request;

import com.artsem.api.rideservice.model.PaymentMethod;
import com.artsem.api.rideservice.model.RideTariff;
import com.artsem.api.rideservice.util.ValidationKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestedRideRequestDto {
    private Long passengerId;
    @NotBlank(message = ValidationKeys.PICKUP_LOCATION_REQUIRED)
    private String pickUpLocation;
    @NotBlank(message = ValidationKeys.DROP_OFF_LOCATION_REQUIRED)
    private String dropOffLocation;
    @NotNull(message = ValidationKeys.DISTANCE_REQUIRED)
    @Positive(message = ValidationKeys.DISTANCE_POSITIVE)
    private Double distance;
    @NotNull(message = ValidationKeys.PRICE_REQUIRED)
    @PositiveOrZero(message = ValidationKeys.PRICE_POSITIVE_OR_ZERO)
    private Double price;
    @NotNull(message = ValidationKeys.TARIFF_ID_REQUIRED)
    private RideTariff tariff;
    @NotNull(message = ValidationKeys.PAYMENT_METHOD_ID_REQUIRED)
    private PaymentMethod paymentMethod;
}