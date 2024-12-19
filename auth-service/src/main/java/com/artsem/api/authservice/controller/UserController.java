package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.controller.api.UserApi;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {

    private final KeycloakService keycloakService;
    private final RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/roles")
    @Override
    public ResponseEntity<List<RoleRepresentation>> getRoles(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(roleService.getAllUserRoles(keycloakService.findUserById(id)));
    }

    @PreAuthorize("""
            (hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER') &&
            @userAccessValidator.isUserAuthorizedForId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}/verify-email")
    @Override
    public ResponseEntity<HttpStatus> sendVerifyEmail(
            @PathVariable String id
    ) {
        keycloakService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable String id
    ) {
        keycloakService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/confirm-email")
    @Override
    public ResponseEntity<HttpStatus> confirmEmail(
            @RequestParam String token
    ) {
        keycloakService.confirmEmailStatus(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
