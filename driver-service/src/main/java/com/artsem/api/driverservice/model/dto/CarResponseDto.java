package com.artsem.api.driverservice.model.dto;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Car}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarResponseDto {
    Long id;
    String model;
    String licensePlate;
    CarClass carClass;
}