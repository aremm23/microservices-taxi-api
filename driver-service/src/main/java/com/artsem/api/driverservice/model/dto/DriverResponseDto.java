package com.artsem.api.driverservice.model.dto;

import com.artsem.api.driverservice.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Driver}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DriverResponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String surname;
}