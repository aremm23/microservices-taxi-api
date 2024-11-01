package com.artsem.api.authservice.service;

import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {

    UserResource createUser(UserRegisterDto userRegisterDto);

    AccessTokenResponse getJwt(UserLoginRecord userLoginRecord);

    void sendVerificationEmail(String userId);

    UserResource findUserById(String userId);

    void forgotPassword(String username);

    void deleteUser(String userId);
}
