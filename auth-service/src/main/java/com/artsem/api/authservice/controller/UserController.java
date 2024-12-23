package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "API for managing users in Keycloak")
public class UserController {

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    @Operation(summary = "Get roles of a user", description = "Retrieve all roles assigned to a user by user ID")
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getRoles(
            @Parameter(description = "ID of the user to retrieve roles for")
            @PathVariable
            String id
    ) {
        return ResponseEntity.ok(roleService.getAllUserRoles(keycloakService.findUserById(id)));
    }

    @Operation(summary = "Send verification email", description = "Send a verification email to a user by user ID")
    @PatchMapping("/{id}/verify-email")
    public ResponseEntity<HttpStatus> sendVerifyEmail(
            @Parameter(description = "ID of the user to send the verification email to")
            @PathVariable
            String id
    ) {
        keycloakService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete a user", description = "Delete a user from the system by user ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(
            @Parameter(description = "ID of the user to delete")
            @PathVariable
            String id
    ) {
        keycloakService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Confirm an email", description = "Confirm user email with token")
    @PatchMapping("/confirm-email/{token}")
    public ResponseEntity<HttpStatus> confirmEmail(
            @Parameter(description = "Email confirmation token")
            @PathVariable
            String token) {
        keycloakService.confirmEmailStatus(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
