package com.artsem.api.passengerservice.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String ACTUATOR = "/actuator/**";
    public static final String PASSENGER_SERVICE_DOCS = "/passenger-service-docs/**";
    public static final String ROLE_PREFIX = "ROLE_";
}
