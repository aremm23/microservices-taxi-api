package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.controller.api.AuthApi;
import com.artsem.api.authservice.model.AdminRegisterRequestDto;
import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterRequestDto;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final KeycloakService keycloakService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid UserLoginRecord userLoginRecord) {
        return ResponseEntity.ok(keycloakService.getJwt(userLoginRecord));
    }

    @PostMapping("/register/user")
    @Override
    public ResponseEntity<HttpStatus> createUser(
            @RequestBody
            @Valid
            UserRegisterRequestDto userRegisterRequestDto
    ) {
        userRegistrationService.createUser(userRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/admin")
    @Override
    public ResponseEntity<HttpStatus> createAdmin(
            @RequestBody
            @Valid
            AdminRegisterRequestDto adminRegisterRequestDto
    ) {
        userRegistrationService.createAdmin(adminRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}