package com.artsem.api.rideservice.service.impl;

import com.artsem.api.rideservice.model.RideTariff;
import com.artsem.api.rideservice.model.util.RideCalculatePriceInfo;
import com.artsem.api.rideservice.service.RidePriceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RidePriceServiceImpl implements RidePriceService {

    private static final BigDecimal PRICE_FOR_1000_METRES = new BigDecimal("0.59");
    private static final BigDecimal PRICE_FOR_60_SECONDS = new BigDecimal("0.43");
    private static final BigDecimal BUSINESS_COEFFICIENT = new BigDecimal("1.5");
    private static final BigDecimal COMFORT_COEFFICIENT = new BigDecimal("1.23");
    private static final BigDecimal ECONOM_COEFFICIENT = new BigDecimal("1");

    public BigDecimal calculateRidePrice(RideCalculatePriceInfo rideCalculatePriceInfo) {
        BigDecimal basePrice = calculateBasePrice(
                rideCalculatePriceInfo.distanceValue(),
                rideCalculatePriceInfo.durationValue(),
                rideCalculatePriceInfo.rideTariff()
        );

        return basePrice.setScale(2, RoundingMode.UP);
    }

    private BigDecimal calculateBasePrice(int distance, int duration, RideTariff rideTariff) {
        BigDecimal coefficient = defineCoefficient(rideTariff);
        BigDecimal distanceCost = new BigDecimal(distance).multiply(PRICE_FOR_1000_METRES)
                .divide(new BigDecimal("1000"), RoundingMode.UP);
        BigDecimal durationCost = new BigDecimal(duration).multiply(PRICE_FOR_60_SECONDS)
                .divide(new BigDecimal("60"), RoundingMode.UP);

        return distanceCost.add(durationCost).multiply(coefficient);
    }

    private BigDecimal defineCoefficient(RideTariff rideTariff) {
        return switch (rideTariff) {
            case COMFORT -> COMFORT_COEFFICIENT;
            case BUSINESS -> BUSINESS_COEFFICIENT;
            case ECONOM -> ECONOM_COEFFICIENT;
        };
    }
}