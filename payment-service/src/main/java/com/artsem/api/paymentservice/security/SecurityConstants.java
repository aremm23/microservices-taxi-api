package com.artsem.api.paymentservice.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String ACTUATOR = "/actuator/**";
    public static final String PAYMENT_SERVICE_DOCS = "/payment-service-docs/**";
    public static final String ROLE_PREFIX = "ROLE_";
}
