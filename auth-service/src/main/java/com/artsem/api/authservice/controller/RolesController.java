package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.KeycloakRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    @PutMapping("/{userId}")
    public ResponseEntity<HttpStatus> assignRole(
            @PathVariable("userId") String userId,
            @RequestParam("role") KeycloakRole keycloakRole
    ) {
        roleService.assignRole(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteRole(
            @PathVariable("userId") String userId,
            @RequestParam("role") KeycloakRole keycloakRole
    ) {
        roleService.deleteRoleFromUser(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
