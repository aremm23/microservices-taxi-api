package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.exception.InvalidUserRoleException;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.model.UserRole;
import com.artsem.api.authservice.service.GroupService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.UserRegistrationService;
import com.artsem.api.authservice.service.rabbit.RabbitSender;
import com.artsem.api.authservice.util.KeycloakGroup;
import com.artsem.api.authservice.util.KeycloakRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    private final GroupService groupService;

    private final RabbitSender rabbitSender;

    private UserResource registerUserInKeycloak(UserRegisterDto userRegisterDto) {
        return keycloakService.createUser(userRegisterDto);
    }

    @Override
    public void createUser(UserRegisterDto userRegisterDto) {
        if (userRegisterDto.getUserRole() == UserRole.PASSENGER) {
            createPassenger(userRegisterDto);
        } else if (userRegisterDto.getUserRole() == UserRole.DRIVER) {
            createDriver(userRegisterDto);
        } else {
            throw new InvalidUserRoleException("Invalid role provided");
        }
    }

    private void createPassenger(UserRegisterDto userRegisterDto) {
        UserResource createdUser = registerUserInKeycloak(userRegisterDto);
        roleService.assignRole(KeycloakRole.PASSENGER, createdUser);
        groupService.assignGroupToUser(KeycloakGroup.PASSENGER, createdUser);
        rabbitSender.sendPassenger(userRegisterDto);
    }

    private void createDriver(UserRegisterDto userRegisterDto) {
        UserResource createdUser = registerUserInKeycloak(userRegisterDto);
        roleService.assignRole(KeycloakRole.DRIVER, createdUser);
        groupService.assignGroupToUser(KeycloakGroup.DRIVER, createdUser);
        rabbitSender.sendDriver(userRegisterDto);
    }

    public void createAdmin(UserRegisterDto userRegisterDto) {
        UserResource createdUser = registerUserInKeycloak(userRegisterDto);
        groupService.assignGroupToUser(KeycloakGroup.MANAGER, createdUser);
        roleService.assignRole(KeycloakRole.ADMIN, createdUser);
        // TODO: to be implemented
    }

}
