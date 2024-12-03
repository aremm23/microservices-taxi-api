package com.artsem.api.rideservice.controller.api;

import com.artsem.api.rideservice.model.util.DistanceAndDurationValues;
import com.artsem.api.rideservice.model.util.RideCalculatePriceInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@Tag(name = "Ride Info Controller", description = "API for retrieving ride-related calculations such as distance, duration, and price.")
public interface RideCalculationControllerApi {

    @Operation(
            summary = "Get distance and duration between locations",
            description = "Calculate the distance and estimated duration between the specified origin and destination."
    )
    @ApiResponse(responseCode = "200", description = "Distance and duration values")
    @ApiResponse(responseCode = "400", description = "Validation failed or other exception occurred")
    ResponseEntity<DistanceAndDurationValues> getDistanceAndDuration(
            @Parameter(description = "Starting point of the ride", example = "Минск, ул Тимошенко, д 34")
            String origin,
            @Parameter(description = "Destination point of the ride", example = "Минск, пр Независимости 56")
            String distance
    );

    @Operation(
            summary = "Calculate ride price",
            description = "Calculate the estimated price of a ride based on the provided details."
    )
    @ApiResponse(responseCode = "200", description = "Estimated ride price")
    @ApiResponse(responseCode = "400", description = "Validation failed or other exception occurred")
    ResponseEntity<BigDecimal> calculateRidePrice(
            @Parameter(description = "Details required to calculate the ride price, such as distance and time")
            RideCalculatePriceInfo calculatePriceInfo
    );
}