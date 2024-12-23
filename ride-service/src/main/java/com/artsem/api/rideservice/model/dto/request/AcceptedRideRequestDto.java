package com.artsem.api.rideservice.model.dto.request;

import com.artsem.api.rideservice.util.ValidationKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AcceptedRideRequestDto {
    @NotNull(message = ValidationKeys.DRIVER_ID_REQUIRED)
    private Long driverId;
    @NotNull(message = ValidationKeys.CAR_ID_REQUIRED)
    private Long carId;
    @NotBlank(message = ValidationKeys.CAR_LICENSE_PLATE_REQUIRED)
    private String carLicensePlate;
}