package com.artsem.api.driverservice.model.dto.request;

import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.util.ValidationKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Driver}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DriverUpdateRequestDto {
    @NotBlank(message = ValidationKeys.EMAIL_REQUIRED)
    private String email;

    @Size(max = 50, message = ValidationKeys.FIRSTNAME_MAX_SIZE)
    private String firstname;

    @Size(max = 50, message = ValidationKeys.SURNAME_MAX_SIZE)
    private String surname;
}