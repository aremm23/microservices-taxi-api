package com.artsem.api.rideservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AcceptedRideDto {
    private String rideId;
    private Long driverId;
    private Long carId;
    private String carLicensePlate;
}