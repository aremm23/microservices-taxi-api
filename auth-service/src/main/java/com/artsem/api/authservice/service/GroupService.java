package com.artsem.api.authservice.service;

import com.artsem.api.authservice.util.KeycloakGroup;
import org.keycloak.admin.client.resource.UserResource;

public interface GroupService {

    void assignGroupToUser(KeycloakGroup keycloakGroup, UserResource userResource);

    void deleteGroupFromUser(KeycloakGroup keycloakGroup, UserResource userResource);
}
