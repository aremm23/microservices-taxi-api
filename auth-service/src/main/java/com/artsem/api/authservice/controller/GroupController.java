package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.GroupService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.KeycloakGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
@Tag(name = "Group Controller", description = "API for managing user groups in Keycloak")
public class GroupController {

    private final GroupService groupService;

    private final KeycloakService keycloakService;

    @Operation(summary = "Assign a group to a user", description = "Assign a specific group to a user by user ID")
    @PutMapping("/{userId}")
    public ResponseEntity<HttpStatus> assignGroupToUser(
            @Parameter(description = "ID of the user to assign the group to")
            @PathVariable("userId")
            String userId,
            @Parameter(description = "Group to assign to the user")
            @RequestParam("group")
            KeycloakGroup keycloakGroup
    ) {
        groupService.assignGroupToUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Delete a group from a user", description = "Remove a specific group from a user by user ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteGroupFromUser(
            @Parameter(description = "ID of the user to remove the group from")
            @PathVariable("userId")
            String userId,
            @Parameter(description = "Group to remove from the user")
            @RequestParam("group")
            KeycloakGroup keycloakGroup
    ) {
        groupService.deleteGroupFromUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
