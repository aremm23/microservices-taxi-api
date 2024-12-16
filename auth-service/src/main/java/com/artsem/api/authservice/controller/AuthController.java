package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "API for authentication and user registration")
public class AuthController {

    private final KeycloakService keycloakService;

    private final UserRegistrationService userRegistrationService;

    @Operation(summary = "User login", description = "Authenticate a user and retrieve a JWT token")
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @Parameter(description = "Login details for user authentication")
            @RequestBody
            @Valid
            UserLoginRecord userLoginRecord
    ) {
        return ResponseEntity.ok(keycloakService.getJwt(userLoginRecord));
    }

    @Operation(summary = "Register a new user", description = "Create a new passenger or driver in the system")
    @PostMapping("/register/user")
    public ResponseEntity<HttpStatus> createUser(
            @Parameter(description = "Details of the user to register")
            @RequestBody
            @Valid
            UserRegisterDto userRegisterDto
    ) {
        userRegistrationService.createUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Register a new admin", description = "Create a new admin in the system")
    @PostMapping("/register/admin")
    public ResponseEntity<HttpStatus> createAdmin(
            @Parameter(description = "Details of the admin to register")
            @RequestBody
            @Valid
            UserRegisterDto userRegisterDto
    ) {
        userRegistrationService.createAdmin(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
