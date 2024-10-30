package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    public final KeycloakService keycloakService;

    public final RoleService roleService;

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RoleRepresentation>> getRoles(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getAllUserRoles(keycloakService.findUserById(id)));
    }

    @PatchMapping("/forgot-password")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestParam String username) {
        keycloakService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/verification-email")
    public ResponseEntity<HttpStatus> sendVerifyEmail(@PathVariable String id) {
        keycloakService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        keycloakService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
