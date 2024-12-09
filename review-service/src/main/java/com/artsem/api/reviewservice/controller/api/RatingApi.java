package com.artsem.api.reviewservice.controller.api;

import com.artsem.api.reviewservice.model.dto.response.RatingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rating Controller", description = "Get average rating driver or passenger rating")
public interface RatingApi {

    @Operation(summary = "Get driver rating", description = "Retrieves the average rating for a given driver")
    @ApiResponse(responseCode = "200", description = "Driver rating retrieved successfully")
    ResponseEntity<RatingResponseDto> getDriverRating(
            @Parameter(description = "Driver ID")
            Long driverId
    );

    @Operation(summary = "Get passenger rating", description = "Retrieves the average rating for a given passenger")
    @ApiResponse(responseCode = "200", description = "Passenger rating retrieved successfully")
    ResponseEntity<RatingResponseDto> getPassengerRating(
            @Parameter(description = "Passenger ID")
            Long passengerId
    );
}