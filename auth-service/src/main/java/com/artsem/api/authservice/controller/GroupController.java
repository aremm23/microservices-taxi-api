package com.artsem.api.authservice.controller;

import com.artsem.api.authservice.service.GroupService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.KeycloakGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    private final KeycloakService keycloakService;


    @PutMapping("/{userId}")
    public ResponseEntity<HttpStatus> assignGroupToUser(
            @PathVariable("userId") String userId,
            @RequestParam("group") KeycloakGroup keycloakGroup
    ) {
        groupService.assignGroupToUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteGroupFromUser(
            @PathVariable("userId") String userId,
            @RequestParam("group") KeycloakGroup keycloakGroup
    ) {
        groupService.deleteGroupFromUser(keycloakGroup, keycloakService.findUserById(userId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
