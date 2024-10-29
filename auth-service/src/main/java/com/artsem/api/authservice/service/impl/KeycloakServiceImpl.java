package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.service.kafka.producer.NotificationProducer;
import com.artsem.api.authservice.util.StatusCodeValidator;
import jakarta.ws.rs.core.Response;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final UsersResource usersResource;

    private final UserRepresentation userRepresentation;

    private final CredentialRepresentation credentialRepresentation;

    private final NotificationProducer notificationProducer;

    @Value("${app.keycloak.server-url}")
    private String serverUrl;

    @Value("${app.keycloak.user.client-id}")
    private String userClientId;

    @Value("${app.keycloak.realm}")
    private String realm;

    /**
     * If the notification service is disabled, email notifications will not be sent.
     * This means users will not be able to confirm their actions through email verification.
     * To ensure functionality without email verification, new users will be marked as
     * "email confirmed" by default (`true`) after registration.
     * Similarly, email confirmation status will be set to `true` after password changes.
     */
    @Value("${taxi-api.notification-service.enabled}")
    private boolean isNotificationServiceEnabled;

    public AccessTokenResponse getJwt(UserLoginRecord userLoginRecord) {
        @Cleanup Keycloak userKeycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(userClientId)
                .username(userLoginRecord.username())
                .password(userLoginRecord.password())
                .build();

        return userKeycloak.tokenManager().getAccessToken();
    }

    @Override
    public UserResource createUser(UserRegisterDto userRegisterDto) {
        setUserRepresentation(userRegisterDto);
        @Cleanup Response response = usersResource.create(userRepresentation);
        validateResponse(response);
        var createdUser = usersResource.search(userRegisterDto.getUsername()).get(0);
        sendVerificationEmail(createdUser.getId());
        return findUserById(createdUser.getId());
    }

    private void validateResponse(Response response) {
        log.info("Keycloak response status: {}", response.getStatus());
        StatusCodeValidator.validate(response);
    }

    @Override
    public void sendVerificationEmail(String userId) {
        log.info("Sending verification email...");
        // TODO: to be implemented
        notificationProducer.send();
    }

    @Override
    public void deleteUser(String userId) {
        @Cleanup Response response = usersResource.delete(userId);
        StatusCodeValidator.validate(response);
    }

    public void setUserRepresentation(UserRegisterDto userRegisterDto) {
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userRegisterDto.getUsername());
        userRepresentation.setEmail(userRegisterDto.getEmail());
        userRepresentation.setFirstName(userRegisterDto.getFirstname());
        userRepresentation.setLastName(userRegisterDto.getLastname());
        userRepresentation.setCreatedTimestamp(System.currentTimeMillis());
        //false - if notification service enabled
        //true if not
        // TODO: to be implemented
        userRepresentation.setEmailVerified(!isNotificationServiceEnabled);
        credentialRepresentation.setValue(userRegisterDto.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }

    @Override
    public UserResource findUserById(String userId) {
        return usersResource.get(userId);
    }

    @Override
    public void forgotPassword(String username) {
        UserResource userResource = usersResource.get(usersResource.search(username).get(0).getId());
        log.info("Sending password update email...");
        // TODO: to be implemented
        notificationProducer.send();
    }

}
