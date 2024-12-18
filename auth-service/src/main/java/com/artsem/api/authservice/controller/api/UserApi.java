package com.artsem.api.authservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User Controller", description = "API for managing users in Keycloak")
public interface UserApi {

    @Operation(summary = "Get roles of a user", description = "Retrieve all roles assigned to a user by user ID")
    ResponseEntity<List<RoleRepresentation>> getRoles(
            @Parameter(description = "ID of the user to retrieve roles for")
            String id
    );

    @Operation(summary = "Send verification email", description = "Send a verification email to a user by user ID")
    ResponseEntity<HttpStatus> sendVerifyEmail(
            @Parameter(description = "ID of the user to send the verification email to")
            String id
    );

    @Operation(summary = "Delete a user", description = "Delete a user from the system by user ID")
    ResponseEntity<HttpStatus> deleteUser(
            @Parameter(description = "ID of the user to delete")
            String id
    );

    @Operation(summary = "Confirm an email", description = "Confirm user email with token")
    ResponseEntity<HttpStatus> confirmEmail(
            @Parameter(description = "Email confirmation token")
            String token
    );
}
