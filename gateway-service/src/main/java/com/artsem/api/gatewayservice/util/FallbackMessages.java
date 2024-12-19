package com.artsem.api.gatewayservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FallbackMessages {
    public static final String AUTH_SERVICE_DOWN = "Auth service is unavailable at the moment.";
    public static final String PASSENGER_SERVICE_DOWN = "Passenger service is unavailable at the moment.";
    public static final String DRIVER_SERVICE_DOWN = "Driver service is unavailable at the moment.";
    public static final String RIDE_SERVICE_DOWN = "Ride service is unavailable at the moment.";
    public static final String REVIEW_SERVICE_DOWN = "Review service is unavailable at the moment.";
    public static final String PAYMENT_SERVICE_DOWN = "Payment service is unavailable at the moment.";
}
