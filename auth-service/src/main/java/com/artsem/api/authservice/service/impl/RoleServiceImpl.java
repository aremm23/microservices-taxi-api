package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.util.KeycloakRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RolesResource rolesResource;

    @Override
    public void assignRole(KeycloakRole keycloakRole, UserResource userResource) {
        var role = rolesResource.get(keycloakRole.getRole()).toRepresentation();
        userResource.roles().realmLevel().add(List.of(role));
    }

    @Override
    public void deleteRoleFromUser(KeycloakRole keycloakRole, UserResource userResource) {
        var role = rolesResource.get(keycloakRole.getRole()).toRepresentation();
        userResource.roles().realmLevel().remove(List.of(role));
    }

    @Override
    public List<RoleRepresentation> getAllUserRoles(UserResource userResource) {
        return userResource.roles().realmLevel().listAll();
    }
}
