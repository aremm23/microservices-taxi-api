package com.artsem.api.rideservice.model.dto.internal;

import com.artsem.api.rideservice.model.util.UserRole;
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