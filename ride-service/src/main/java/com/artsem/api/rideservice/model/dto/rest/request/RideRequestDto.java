package com.artsem.api.rideservice.model.dto.rest.request;

import com.artsem.api.rideservice.model.PaymentMethod;
import com.artsem.api.rideservice.model.RideStatus;
import com.artsem.api.rideservice.model.RideTariff;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RideRequestDto {
    private Long passengerId;
    private String pickUpLocation;
    private String dropOffLocation;
    private Long driverId;
    private Long carId;
    private String carLicensePlate;
    private Double distance;
    private Double price;
    private RideTariff tariff;
    private PaymentMethod paymentMethod;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acceptedTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelTime;
    private RideStatus status;
}