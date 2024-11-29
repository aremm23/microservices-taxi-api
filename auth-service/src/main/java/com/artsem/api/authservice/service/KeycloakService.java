package com.artsem.api.authservice.service;

import com.artsem.api.authservice.model.UserIdsMessage;
import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {

    UserResource createUser(UserRegisterDto userRegisterDto);

    AccessTokenResponse getJwt(UserLoginRecord userLoginRecord);

    void sendVerificationEmail(String userId);

    void setUserSequenceId(UserIdsMessage userIdsMessage);

    UserResource findUserById(String userId);

    void deleteUser(String userId);

    void confirmEmailStatus(String token);
}
