package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.KeycloakRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles Controller", description = "API for managing user roles")
public class RolesController {

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    @Operation(summary = "Assign a role to a user", description = "Assign a specified role to a user by user ID")
    @PutMapping("/{userId}")
    public ResponseEntity<HttpStatus> assignRole(
            @Parameter(description = "ID of the user to assign the role to")
            @PathVariable("userId")
            String userId,
            @Parameter(description = "Role to assign to the user")
            @RequestParam("role")
            KeycloakRole keycloakRole
    ) {
        roleService.assignRole(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete a role from a user", description = "Remove a specified role from a user by user ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteRole(
            @Parameter(description = "ID of the user to remove the role from")
            @PathVariable("userId")
            String userId,
            @Parameter(description = "Role to remove from the user")
            @RequestParam("role")
            KeycloakRole keycloakRole
    ) {
        roleService.deleteRoleFromUser(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}