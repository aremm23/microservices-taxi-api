package com.artsem.api.rideservice.security;

import com.artsem.api.rideservice.service.RideBasicService;
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

    private final RideBasicService rideBasicService;

    public boolean isPassengerAuthorizedForRideId(String rideId, Authentication authentication) {
        String userSequenceId = getUserSequenceId(authentication, USER_SEQUENCE_ID_CLAIM);
        if (userSequenceId == null) return false;
        Long userIdByRideId = rideBasicService.getPassengerIdByRideId(rideId);
        log.info("Passenger sequence id: {}. Passenger id by ride id: {}", userSequenceId, userIdByRideId);
        return userIdByRideId.toString().equals(userSequenceId);
    }

    public boolean isDriverAuthorizedForRideId(String rideId, Authentication authentication) {
        String userSequenceId = getUserSequenceId(authentication, USER_SEQUENCE_ID_CLAIM);
        if (userSequenceId == null) return false;
        Long userIdByRideId = rideBasicService.getDriverIdByRideId(rideId);
        log.info("Driver sequence id: {}. Driver id by ride id: {}", userSequenceId, userIdByRideId);
        return userIdByRideId.toString().equals(userSequenceId);
    }

    public boolean isUserAuthorizedForId(String userId, Authentication authentication) {
        String userSequenceId = getUserSequenceId(authentication, USER_SEQUENCE_ID_CLAIM);
        if (userSequenceId == null) return false;
        log.info("Driver sequence id: {}.", userSequenceId);
        return userId.equals(userSequenceId);
    }

    private String getUserSequenceId(Authentication authentication, String userSequenceIdClaim) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            log.warn("Authentication is null or principal is not Jwt");
            return null;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString(userSequenceIdClaim);
    }

    public boolean isClientAuthorizedForRequest(String expectedClientId, Authentication authentication) {
        String clientId = getUserSequenceId(authentication, CLIENT_ID_CLAIM);
        if (clientId == null) return false;
        log.info("Client id from token: {}", clientId);
        return expectedClientId.equals(clientId);
    }
}
