package com.artsem.api.authservice.controller.api;

import com.artsem.api.authservice.util.KeycloakRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(name = "Roles Controller", description = "API for managing user roles")
public interface RolesApi {

    @Operation(summary = "Assign a role to a user", description = "Assign a specified role to a user by user ID")
    ResponseEntity<HttpStatus> assignRole(
            @Parameter(description = "ID of the user to assign the role to")
            String userId,
            @Parameter(description = "Role to assign to the user")
            KeycloakRole keycloakRole
    );

    @Operation(summary = "Delete a role from a user", description = "Remove a specified role from a user by user ID")
    ResponseEntity<HttpStatus> deleteRole(
            @Parameter(description = "ID of the user to remove the role from")
            String userId,
            @Parameter(description = "Role to remove from the user")
            KeycloakRole keycloakRole
    );
}
