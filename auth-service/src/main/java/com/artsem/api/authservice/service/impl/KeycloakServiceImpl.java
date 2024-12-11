package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.broker.EmailVerificationMessage;
import com.artsem.api.authservice.broker.producer.EmailVerifyNotificationProducer;
import com.artsem.api.authservice.exception.UserNotFoundException;
import com.artsem.api.authservice.model.UserIdsMessage;
import com.artsem.api.authservice.model.UserLoginRecord;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.service.JwtService;
import com.artsem.api.authservice.service.KeycloakService;
import com.artsem.api.authservice.util.ExceptionKeys;
import com.artsem.api.authservice.util.StatusCodeValidator;
import jakarta.ws.rs.core.Response;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    public static final String USER_SEQUENCE_ID_ATTRIBUTE = "user_sequence_id";

    private final UsersResource usersResource;

    private final UserRepresentation userRepresentation;

    private final CredentialRepresentation credentialRepresentation;

    private final EmailVerifyNotificationProducer emailVerifyNotificationProducer;

    private final JwtService jwtService;

    private final String serverUrl;

    private final String userClientId;

    private final String realm;

    public KeycloakServiceImpl(
            UsersResource usersResource,
            UserRepresentation userRepresentation,
            CredentialRepresentation credentialRepresentation,
            EmailVerifyNotificationProducer emailVerifyNotificationProducer,
            JwtService jwtService,
            @Value("${app.keycloak.server-url}") String serverUrl,
            @Value("${app.keycloak.user.client-id}") String userClientId,
            @Value("${app.keycloak.realm}") String realm
    ) {
        this.usersResource = usersResource;
        this.userRepresentation = userRepresentation;
        this.credentialRepresentation = credentialRepresentation;
        this.emailVerifyNotificationProducer = emailVerifyNotificationProducer;
        this.jwtService = jwtService;
        this.serverUrl = serverUrl;
        this.userClientId = userClientId;
        this.realm = realm;
    }

    public AccessTokenResponse getJwt(UserLoginRecord userLoginRecord) {
        try (
                Keycloak userKeycloak = KeycloakBuilder.builder()
                        .serverUrl(serverUrl)
                        .realm(realm)
                        .grantType(OAuth2Constants.PASSWORD)
                        .clientId(userClientId)
                        .username(userLoginRecord.username())
                        .password(userLoginRecord.password())
                        .build()
        ) {
            return userKeycloak.tokenManager().getAccessToken();
        }
    }

    @Override
    public UserResource createUser(UserRegisterDto userRegisterDto) {
        setUserRepresentation(userRegisterDto);
        try (Response response = usersResource.create(userRepresentation)) {
            validateResponse(response);
        }
        var createdUser = usersResource.search(userRegisterDto.getUsername()).get(0);
        sendVerificationEmail(createdUser.getId());
        return findUserById(createdUser.getId());
    }

    public void setUserSequenceId(UserIdsMessage userIdsMessage) {
        UserResource userResource = usersResource.get(userIdsMessage.userKeycloakId());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        setUserAttribute(userIdsMessage.userSequenceId(), userRepresentation);
        userResource.update(userRepresentation);
    }

    private void setUserAttribute(String id, UserRepresentation userRepresentation) {
        Map<String, List<String>> attributes = userRepresentation.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(USER_SEQUENCE_ID_ATTRIBUTE, List.of(id));
        userRepresentation.setAttributes(attributes);
    }

    private void validateResponse(Response response) {
        log.info("Keycloak response status: {}", response.getStatus());
        StatusCodeValidator.validate(response);
    }

    @Override
    public void sendVerificationEmail(String userId) {
        UserRepresentation user = findUserById(userId).toRepresentation();
        String confirmationToken = jwtService.generateEmailConfirmationToken(user.getEmail());
        emailVerifyNotificationProducer.publishEmailVerifyMessage(buildEmailVerificationMessage(
                user.getEmail(),
                confirmationToken
        ));
    }

    private EmailVerificationMessage buildEmailVerificationMessage(String email, String confirmationToken) {
        return EmailVerificationMessage.builder()
                .email(email)
                .token(confirmationToken)
                .build();
    }

    @Override
    public void deleteUser(String userId) {
        try (Response response = usersResource.delete(userId)) {
            StatusCodeValidator.validate(response);
        }
    }

    public void setUserRepresentation(UserRegisterDto userRegisterDto) {
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userRegisterDto.getUsername());
        userRepresentation.setEmail(userRegisterDto.getEmail());
        userRepresentation.setFirstName(userRegisterDto.getFirstname());
        userRepresentation.setLastName(userRegisterDto.getLastname());
        userRepresentation.setCreatedTimestamp(System.currentTimeMillis());
        userRepresentation.setEmailVerified(false);
        credentialRepresentation.setValue(userRegisterDto.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }

    @Override
    public UserResource findUserById(String userId) {
        return usersResource.get(userId);
    }

    @Override
    public void confirmEmailStatus(String token) {
        String email = jwtService.getEmailFromToken(token);
        UserRepresentation user = extractUserRepresentationByEmail(email);
        UserResource userResource = usersResource.get(user.getId());
        user.setEmailVerified(true);
        userResource.update(user);
    }

    private UserRepresentation extractUserRepresentationByEmail(String email) {
        List<UserRepresentation> users = usersResource.search(email, 0, 1);
        if (users.isEmpty()) {
            throw new UserNotFoundException(ExceptionKeys.INVALID_USER_EMAIL);
        }
        return users.get(0);
    }

}