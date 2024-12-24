package com.artsem.api.authservice.controller.api;

import com.artsem.api.authservice.util.KeycloakGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(name = "Group Controller", description = "API for managing user groups in Keycloak")
public interface GroupApi {

    @Operation(summary = "Assign a group to a user", description = "Assign a specific group to a user by user ID")
    ResponseEntity<HttpStatus> assignGroupToUser(
            @Parameter(description = "ID of the user to assign the group to")
            String userId,
            @Parameter(description = "Group to assign to the user")
            KeycloakGroup keycloakGroup
    );

    @Operation(summary = "Delete a group from a user", description = "Remove a specific group from a user by user ID")
    ResponseEntity<HttpStatus> deleteGroupFromUser(
            @Parameter(description = "ID of the user to remove the group from")
            String userId,
            @Parameter(description = "Group to remove from the user")
            KeycloakGroup keycloakGroup
    );
}
