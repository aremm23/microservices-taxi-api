package com.artsem.api.driverservice.model.dto.request;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarCategory;
import com.artsem.api.driverservice.util.PatternUtils;
import com.artsem.api.driverservice.util.ValidationKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Car}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CarRequestDto {
    @NotBlank(message = ValidationKeys.MODEL_REQUIRED)
    private String model;

    @Pattern(message = ValidationKeys.INVALID_LICENSE_PLATE_FORMAT, regexp = PatternUtils.PLATE_NUMBER_PATTERN)
    @NotBlank(message = ValidationKeys.LICENSE_PLATE_REQUIRED)
    private String licensePlate;

    @NotNull(message = ValidationKeys.CAR_CATEGORY_REQUIRED)
    private CarCategory carCategory;
}