package com.artsem.api.rideservice.model.dto.request;

import com.artsem.api.rideservice.model.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CancelledRideRequestDto {
    private UserRole userRole;
}