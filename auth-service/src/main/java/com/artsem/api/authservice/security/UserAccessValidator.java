package com.artsem.api.authservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAccessValidator {

    public static final String SUB_CLAIM = "sub";

    public boolean isUserAuthorizedForId(String pathId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return false;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString(SUB_CLAIM);
        log.info("User id: {}", userId);
        return pathId.equals(userId);
    }

}