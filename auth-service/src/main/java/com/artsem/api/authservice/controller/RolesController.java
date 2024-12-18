package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.controller.api.RolesApi;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.util.KeycloakRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController implements RolesApi {

    private final KeycloakService keycloakService;
    private final RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}")
    @Override
    public ResponseEntity<HttpStatus> assignRole(
            @PathVariable("userId") String userId,
            @RequestParam("role") KeycloakRole keycloakRole
    ) {
        roleService.assignRole(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<HttpStatus> deleteRole(
            @PathVariable("userId") String userId,
            @RequestParam("role") KeycloakRole keycloakRole
    ) {
        roleService.deleteRoleFromUser(keycloakRole, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
