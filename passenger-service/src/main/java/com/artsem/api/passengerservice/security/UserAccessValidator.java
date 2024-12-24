package com.artsem.api.passengerservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAccessValidator {

    public static final String USER_SEQUENCE_ID_CLAIM = "user_sequence_id";
    public static final String CLIENT_ID_CLAIM = "client_id";

    public boolean isUserAuthorizedForId(Long pathId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return false;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userSequenceId = jwt.getClaimAsString(USER_SEQUENCE_ID_CLAIM);
        log.info("User sequence id: {}", userSequenceId);
        return pathId.toString().equals(userSequenceId);
    }

    public boolean isClientAuthorizedForRequest(String expectedClientId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return false;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String clientId = jwt.getClaimAsString(CLIENT_ID_CLAIM);
        log.info("Client id from token: {}", clientId);
        return expectedClientId.equals(clientId);
    }
}