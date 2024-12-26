package com.artsem.api.paymentservice.security;

import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAccessValidator {

    public static final String USER_SEQUENCE_ID_CLAIM = "user_sequence_id";
    public static final String CLIENT_ID_CLAIM = "client_id";

    private final BalanceService balanceService;

    public boolean isPassengerAuthorizedForBalanceId(Long balanceId, Authentication authentication) {
        String userSequenceId = getClaim(authentication, USER_SEQUENCE_ID_CLAIM);
        if (userSequenceId == null) return false;
        Long userIdByRideId = balanceService.getById(balanceId).getUserId();
        log.info("Passenger sequence id: {}. Passenger id by balance id: {}", userSequenceId, userIdByRideId);
        return userIdByRideId.toString().equals(userSequenceId);
    }

    public boolean isUserAuthorizedForId(String userId, Authentication authentication) {
        String userSequenceId = getClaim(authentication, USER_SEQUENCE_ID_CLAIM);
        if (userSequenceId == null) return false;
        log.info("User sequence id: {}.", userSequenceId);
        return userId.equals(userSequenceId);
    }

    private String getClaim(Authentication authentication, String claimName) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            log.warn("Authentication is null or principal is not Jwt");
            return null;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString(claimName);
    }

    public boolean isClientAuthorizedForRequest(String expectedClientId, Authentication authentication) {
        String clientId = getClaim(authentication, CLIENT_ID_CLAIM);
        if (clientId == null) return false;
        log.info("Client id from token: {}", clientId);
        return expectedClientId.equals(clientId);
    }

}