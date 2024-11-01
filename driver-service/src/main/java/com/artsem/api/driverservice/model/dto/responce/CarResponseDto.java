package com.artsem.api.driverservice.model.dto.responce;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarCategory;
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
    private Long id;
    private String model;
    private String licensePlate;
    private CarCategory carCategory;
}