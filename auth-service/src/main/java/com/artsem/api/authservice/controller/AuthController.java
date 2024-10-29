package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.UserRegistrationService;
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
public class AuthController {

    public final KeycloakService keycloakService;

    public final RoleService roleService;

    public final UserRegistrationService userRegistrationService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody @Valid UserLoginRecord userLoginRecord) {
        return ResponseEntity.ok(keycloakService.getJwt(userLoginRecord));
    }

    @PostMapping("/register-passenger")
    public ResponseEntity<?> createPassenger(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userRegistrationService.createPassenger(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userRegistrationService.createAdmin(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/register-driver")
    public ResponseEntity<?> createDriver(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userRegistrationService.createDriver(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
