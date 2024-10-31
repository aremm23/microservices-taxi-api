package com.artsem.api.driverservice.model.dto.request;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Car}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarRequestDto {
    @NotBlank(message = "Model should not be blanked")
    String model;

    @Pattern(message = "Only belarusian plate number valid", regexp = "^\\d{4}[A-Z]{2}-\\d{1}$")
    @NotBlank(message = "Plate number should not be blanked")
    String licensePlate;

    @NotNull(message = "Car class should not be null")
    CarClass carClass;
}