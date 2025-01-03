package com.artsem.api.authservice.model;

import com.artsem.api.authservice.util.ValidationKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AdminRegisterRequestDto(
        @NotBlank(message = ValidationKeys.USERNAME_REQUIRED)
        @Size(max = 15, message = ValidationKeys.USERNAME_MAX_SIZE)
        String username,
        @NotBlank(message = ValidationKeys.PASSWORD_REQUIRED)
        @Size(min = 8, max = 128, message = ValidationKeys.PASSWORD_MIN_SIZE)
        String password,
        @Email(message = ValidationKeys.INVALID_EMAIL_FORMAT)
        @NotBlank(message = ValidationKeys.EMAIL_REQUIRED)
        String email,
        @NotBlank(message = ValidationKeys.FIRSTNAME_REQUIRED)
        @Size(max = 50, message = ValidationKeys.FIRSTNAME_MAX_SIZE)
        String firstname,
        @NotBlank(message = ValidationKeys.SURNAME_REQUIRED)
        @Size(max = 50, message = ValidationKeys.SURNAME_MAX_SIZE)
        String lastname
) {
}
