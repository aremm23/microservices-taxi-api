package com.artsem.api.reviewservice.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String ACTUATOR = "/actuator/**";
    public static final String REVIEW_SERVICE_DOCS = "/review-service-docs/**";
    public static final String ROLE_PREFIX = "ROLE_";
}
