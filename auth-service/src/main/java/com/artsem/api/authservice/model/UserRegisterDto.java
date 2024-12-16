package com.artsem.api.authservice.model;

import com.artsem.api.authservice.util.ValidationKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public final class UserRegisterDto {

    @NotBlank(message = ValidationKeys.USERNAME_REQUIRED)
    @Size(max = 15, message = ValidationKeys.USERNAME_MAX_SIZE)
    private String username;

    @NotBlank(message = ValidationKeys.PASSWORD_REQUIRED)
    @Size(min = 8, max = 128, message = ValidationKeys.PASSWORD_MIN_SIZE)
    private String password;

    @Email(message = ValidationKeys.INVALID_EMAIL_FORMAT)
    @NotBlank(message = ValidationKeys.EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = ValidationKeys.FIRSTNAME_REQUIRED)
    @Size(max = 50, message = ValidationKeys.FIRSTNAME_MAX_SIZE)
    private String firstname;

    @NotBlank(message = ValidationKeys.SURNAME_REQUIRED)
    @Size(max = 50, message = ValidationKeys.SURNAME_MAX_SIZE)
    private String lastname;

    @NotBlank(message = ValidationKeys.USER_ROLE_REQUIRED)
    private UserRole userRole;
}
