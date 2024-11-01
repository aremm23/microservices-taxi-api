package com.artsem.api.driverservice.model.dto.request;

import com.artsem.api.driverservice.model.CarCategory;
import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.util.PatternUtils;
import com.artsem.api.driverservice.util.ValidationKeys;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Driver}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarUpdateRequestDto {
    private String model;

    @Pattern(message = ValidationKeys.INVALID_LICENSE_PLATE_FORMAT, regexp = PatternUtils.PLATE_NUMBER_PATTERN)
    private String licensePlate;

    private CarCategory carCategory;
}