package com.artsem.api.rideservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationKeys {
    public static final String PASSENGER_ID_REQUIRED = "passenger-id-required";
    public static final String PICKUP_LOCATION_REQUIRED = "pickup-location-required";
    public static final String DROP_OFF_LOCATION_REQUIRED = "drop-off-location-required";
    public static final String DRIVER_ID_REQUIRED = "driver-id-required";
    public static final String CAR_ID_REQUIRED = "car-id-required";
    public static final String CAR_LICENSE_PLATE_REQUIRED = "car-license-plate-required";
    public static final String DISTANCE_REQUIRED = "distance-required";
    public static final String DISTANCE_POSITIVE = "distance-positive";
    public static final String PRICE_REQUIRED = "price-required";
    public static final String PRICE_POSITIVE_OR_ZERO = "price-positive-or-zero";
    public static final String TARIFF_ID_REQUIRED = "tariff-id-required";
    public static final String PAYMENT_METHOD_ID_REQUIRED = "payment-method-id-required";
    public static final String REQUESTED_TIME_REQUIRED = "requested-time-required";
    public static final String STATUS_ID_REQUIRED = "status-id-required";
    public static final String UNKNOWN_MESSAGE = "unknown-message";
}
