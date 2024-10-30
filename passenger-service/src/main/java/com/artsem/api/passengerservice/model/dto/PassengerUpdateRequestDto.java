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
public class PassengerUpdateRequestDto {
    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstname;

    @Size(max = 50, message = "Surname must not exceed 50 characters")
    private String surname;
}