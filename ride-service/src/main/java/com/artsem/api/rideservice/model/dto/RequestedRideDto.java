package com.artsem.api.rideservice.model.dto;

import com.artsem.api.rideservice.model.PaymentMethod;
import com.artsem.api.rideservice.model.RideTariff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestedRideDto {
    private Long passengerId;
    private String pickUpLocation;
    private String dropOffLocation;
    private Double distance;
    private Double price;
    private RideTariff tariff;
    private PaymentMethod paymentMethod;
}