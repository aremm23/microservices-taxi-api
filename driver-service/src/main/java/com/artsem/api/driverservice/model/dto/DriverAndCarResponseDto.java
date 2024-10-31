package com.artsem.api.driverservice.model.dto;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Car} and {@link Driver}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DriverAndCarResponseDto {
    Long id;
    String email;
    String firstname;
    String surname;
    CarResponseDto car;
}