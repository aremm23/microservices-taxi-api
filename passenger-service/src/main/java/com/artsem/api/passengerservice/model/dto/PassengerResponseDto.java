package com.artsem.api.passengerservice.model.dto;

import com.artsem.api.passengerservice.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Passenger}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassengerResponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String surname;
}