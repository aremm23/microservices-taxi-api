package com.artsem.api.authservice.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String ACTUATOR_URI = "/actuator/**";
    public static final String AUTH_SERVICE_DOCS_URI = "/auth-service-docs/**";
    public static final String USER_REGISTER_URI = "/api/v1/auth/register/user";
    public static final String USER_LOGIN_URI = "/api/v1/auth/login";
    public static final String CONFIRM_EMAIL_URL = "/api/v1/users/confirm-email";
    public static final String ROLE_PREFIX = "ROLE_";
}
