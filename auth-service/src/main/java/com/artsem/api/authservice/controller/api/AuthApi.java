package com.artsem.api.authservice.controller.api;

import com.artsem.api.authservice.model.AdminRegisterRequestDto;
import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth Controller", description = "API for authentication and user registration")
public interface AuthApi {

    @Operation(summary = "User login", description = "Authenticate a user and retrieve a JWT token")
    ResponseEntity<AccessTokenResponse> login(
            @Parameter(description = "Login details for user authentication")
            UserLoginRecord userLoginRecord
    );

    @Operation(summary = "Register a new user", description = "Create a new passenger or driver in the system")
    ResponseEntity<HttpStatus> createUser(
            @Parameter(description = "Details of the user to register")
            UserRegisterRequestDto userRegisterRequestDto
    );

    @Operation(summary = "Register a new admin", description = "Create a new admin in the system")
    ResponseEntity<HttpStatus> createAdmin(
            @Parameter(description = "Details of the admin to register")
            AdminRegisterRequestDto adminRegisterRequestDto
    );
}