package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.controller.api.GroupApi;
import com.artsem.api.authservice.service.GroupService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.KeycloakGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController implements GroupApi {

    private final GroupService groupService;
    private final KeycloakService keycloakService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{userId}")
    @Override
    public ResponseEntity<HttpStatus> assignGroupToUser(
            @PathVariable("userId") String userId,
            @RequestParam("group") KeycloakGroup keycloakGroup
    ) {
        groupService.assignGroupToUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<HttpStatus> deleteGroupFromUser(
            @PathVariable("userId") String userId,
            @RequestParam("group") KeycloakGroup keycloakGroup
    ) {
        groupService.deleteGroupFromUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
