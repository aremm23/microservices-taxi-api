package com.artsem.api.driverservice.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String ACTUATOR = "/actuator/**";
    public static final String DRIVER_SERVICE_DOCS = "/driver-service-docs/**";
    public static final String ROLE_PREFIX = "ROLE_";
}
