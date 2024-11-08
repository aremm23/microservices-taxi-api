package com.artsem.api.rideservice.model.dto;

import com.artsem.api.rideservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CancelledRideDto {
    private String rideId;
    private UserRole userRole;
}