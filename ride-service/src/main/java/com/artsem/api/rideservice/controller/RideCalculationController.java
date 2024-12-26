package com.artsem.api.rideservice.controller;

import com.artsem.api.rideservice.controller.api.RideCalculationControllerApi;
import com.artsem.api.rideservice.model.util.DistanceAndDurationValues;
import com.artsem.api.rideservice.model.util.RideCalculatePriceInfo;
import com.artsem.api.rideservice.service.RideDistanceService;
import com.artsem.api.rideservice.service.RidePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/rides/calculations")
@RequiredArgsConstructor
public class RideCalculationController implements RideCalculationControllerApi {

    private final RideDistanceService rideDistanceService;

    private final RidePriceService ridePriceService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER')")
    @GetMapping("/distance")
    public ResponseEntity<DistanceAndDurationValues> getDistanceAndDuration(
            @RequestParam(name = "origin") String origin,
            @RequestParam(name = "distance") String distance
    ) {
        return ResponseEntity.ok(rideDistanceService.getDistanceAndDuration(origin, distance));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER')")
    @GetMapping("/price")
    public ResponseEntity<BigDecimal> calculateRidePrice(
            @RequestBody RideCalculatePriceInfo calculatePriceInfo
    ) {
        return ResponseEntity.ok(ridePriceService.calculateRidePrice(calculatePriceInfo));
    }

}
