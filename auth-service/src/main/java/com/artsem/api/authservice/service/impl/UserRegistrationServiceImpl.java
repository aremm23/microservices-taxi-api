package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.broker.producer.UserCreatedProducer;
import com.artsem.api.authservice.config.RegisterMapper;
import com.artsem.api.authservice.exception.InvalidUserRoleException;
import com.artsem.api.authservice.model.AdminRegisterRequestDto;
import com.artsem.api.authservice.model.UserCreateMessage;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.model.UserRegisterRequestDto;
import com.artsem.api.authservice.model.UserRole;
import com.artsem.api.authservice.service.GroupService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.RoleService;
import com.artsem.api.authservice.service.UserRegistrationService;
import com.artsem.api.authservice.util.KeycloakGroup;
import com.artsem.api.authservice.util.KeycloakRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    public static final String PASSENGER_ROLE_LOWERCASE = "passenger";
    public static final String DRIVER_ROLE_LOWERCASE = "driver";

    private final KeycloakService keycloakService;

    private final RoleService roleService;

    private final GroupService groupService;

    private final UserCreatedProducer userCreatedProducer;

    private UserResource registerUserInKeycloak(UserRegisterDto userRegisterDto) {
        return keycloakService.createUser(userRegisterDto);
    }

    @Override
    public void createUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userRegisterRequestDto.userRole() == UserRole.PASSENGER) {
            createPassenger(RegisterMapper.INSTANCE.toUserRegisterDto(userRegisterRequestDto));
        } else if (userRegisterRequestDto.userRole() == UserRole.DRIVER) {
            createDriver(RegisterMapper.INSTANCE.toUserRegisterDto(userRegisterRequestDto));
        } else {
            throw new InvalidUserRoleException();
        }
    }

    private void createPassenger(UserRegisterDto userRegisterDto) {
        UserResource createdUser = registerUserInKeycloak(userRegisterDto);
        roleService.assignRole(KeycloakRole.PASSENGER, createdUser);
        groupService.assignGroupToUser(KeycloakGroup.PASSENGER, createdUser);
        userCreatedProducer.publishUserCreatedEvent(PASSENGER_ROLE_LOWERCASE, buildUserCreateMessage(createdUser));
    }

    private void createDriver(UserRegisterDto userRegisterDto) {
        UserResource createdUser = registerUserInKeycloak(userRegisterDto);
        roleService.assignRole(KeycloakRole.DRIVER, createdUser);
        groupService.assignGroupToUser(KeycloakGroup.DRIVER, createdUser);
        userCreatedProducer.publishUserCreatedEvent(DRIVER_ROLE_LOWERCASE, buildUserCreateMessage(createdUser));
    }

    @Override
    public void createAdmin(AdminRegisterRequestDto adminRegisterRequestDto) {
        UserResource createdUser = registerUserInKeycloak(RegisterMapper.INSTANCE.toUserRegisterDto(adminRegisterRequestDto));
        groupService.assignGroupToUser(KeycloakGroup.MANAGER, createdUser);
        roleService.assignRole(KeycloakRole.ADMIN, createdUser);
    }

    private UserCreateMessage buildUserCreateMessage(UserResource createdUser) {
        UserRepresentation createdUserRepresentation = createdUser.toRepresentation();
        return UserCreateMessage.builder()
                .lastname(createdUserRepresentation.getLastName())
                .firstname(createdUserRepresentation.getFirstName())
                .email(createdUserRepresentation.getEmail())
                .keycloakId(createdUserRepresentation.getId())
                .build();
    }

}
