package com.artsem.api.driverservice.model.dto.response;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Car} and {@link Driver}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DriverAndCarResponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String surname;
    private CarResponseDto car;
}