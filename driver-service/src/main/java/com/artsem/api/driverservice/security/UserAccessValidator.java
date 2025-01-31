package com.artsem.api.driverservice.security;

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
        String userSequenceId = getClaim(authentication, USER_SEQUENCE_ID_CLAIM);
        log.info("User sequence id: {}", userSequenceId);
        return pathId.toString().equals(userSequenceId);
    }

    private String getClaim(Authentication authentication, String userSequenceIdClaim) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            log.warn("Authentication is null or principal is not Jwt");
            return null;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString(userSequenceIdClaim);
    }

    public boolean isClientAuthorizedForRequest(String expectedClientId, Authentication authentication) {
        String clientId = getClaim(authentication, CLIENT_ID_CLAIM);
        if (clientId == null) return false;
        log.info("Client id from token: {}", clientId);
        return expectedClientId.equals(clientId);
    }

}