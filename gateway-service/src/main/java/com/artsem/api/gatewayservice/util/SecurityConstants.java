package com.artsem.api.gatewayservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String AUTH_LOGIN = "/api/v1/auth/login";
    public static final String AUTH_REGISTER = "/api/v1/auth/register/**";
    public static final String ACTUATOR_HEALTH = "/actuator/health";
    public static final String AUTH_SERVICE_ACTUATOR = "/auth-service/actuator/**";
    public static final String PASSENGER_SERVICE_ACTUATOR = "/passenger-service/actuator/**";
    public static final String DRIVER_SERVICE_ACTUATOR = "/driver-service/actuator/**";
    public static final String NOTIFICATION_SERVICE_ACTUATOR = "/notification-service/actuator/**";
    public static final String PAYMENT_SERVICE_ACTUATOR = "/payment-service/actuator/**";
    public static final String RIDE_SERVICE_ACTUATOR = "/ride-service/actuator/**";
    public static final String REVIEW_SERVICE_ACTUATOR = "/review-service/actuator/**";
    public static final String FALLBACK = "/fallback/**";
}