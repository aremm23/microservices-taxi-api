package com.artsem.api.passengerservice.model.dto;

import com.artsem.api.passengerservice.model.Passenger;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Passenger}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassengerRequestDto {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "First name must not be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstname;

    @NotBlank(message = "Surname must not be blank")
    @Size(max = 50, message = "Surname must not exceed 50 characters")
    private String surname;
}