package com.artsem.api.authservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserRegisterDto {

    @NotBlank(message = "Username must not be blank")
    @Size(max = 15, message = "Username must not exceed 15 characters")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 128, message = "Password must be at least 8 characters")
    private String password;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "First name must not be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstname;

    @NotBlank(message = "Surname must not be blank")
    @Size(max = 50, message = "Surname must not exceed 50 characters")
    private String lastname;

}