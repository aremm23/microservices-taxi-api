package com.artsem.api.rideservice.controller.api;

import com.artsem.api.rideservice.filter.RideFilter;
import com.artsem.api.rideservice.model.dto.request.RideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Ride Controller", description = "API for managing rides in the ride service")
public interface BasicRideControllerApi {

    @Operation(summary = "Get a list of rides", description = "Retrieve a paginated list of rides with optional filters")
    ResponseEntity<PagedModel<RideResponseDto>> getList(
            @Parameter(description = "Filters for ride search")
            RideFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    );

    @Operation(summary = "Get ride details by ID", description = "Retrieve detailed information about a specific ride")
    ResponseEntity<RideResponseDto> getById(
            @Parameter(description = "ID of the ride to retrieve")
            String id
    );

    @Operation(summary = "Create a new ride", description = "Create and register a new ride in the system")
    ResponseEntity<RideResponseDto> create(
            @Parameter(description = "Details of the ride to create")
            RideRequestDto rideDto
    );

    @Operation(summary = "Delete a ride by ID", description = "Remove a ride from the system by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the ride to delete")
            String id
    );
}