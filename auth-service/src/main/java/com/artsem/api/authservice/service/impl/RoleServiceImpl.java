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

    private List<RoleRepresentation> getRoleRepresentationList(KeycloakRole keycloakRole) {
        return List.of(rolesResource.get(keycloakRole.getRole()).toRepresentation());
    }

    @Override
    public void assignRole(KeycloakRole keycloakRole, UserResource userResource) {
        List<RoleRepresentation> rolesList = getRoleRepresentationList(keycloakRole);
        userResource.roles().realmLevel().add(rolesList);
    }

    @Override
    public void deleteRoleFromUser(KeycloakRole keycloakRole, UserResource userResource) {
        List<RoleRepresentation> rolesList = getRoleRepresentationList(keycloakRole);
        userResource.roles().realmLevel().remove(rolesList);
    }

    @Override
    public List<RoleRepresentation> getAllUserRoles(UserResource userResource) {
        return userResource.roles().realmLevel().listAll();
    }
}
