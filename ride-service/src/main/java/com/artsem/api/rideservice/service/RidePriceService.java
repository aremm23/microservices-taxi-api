package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.model.util.RideCalculatePriceInfo;

import java.math.BigDecimal;

public interface RidePriceService {

    BigDecimal calculateRidePrice(RideCalculatePriceInfo rideCalculatePriceInfo);

}