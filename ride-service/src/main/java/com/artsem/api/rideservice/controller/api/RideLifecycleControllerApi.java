package com.artsem.api.rideservice.controller.api;

import com.artsem.api.rideservice.model.dto.request.AcceptedRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.CancelledRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.RequestedRideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Ride Lifecycle Controller", description = "API for managing the lifecycle of rides in the ride service")
public interface RideLifecycleControllerApi {

    @Operation(summary = "Request a ride", description = "Request a new ride")
    @PostMapping("/request")
    ResponseEntity<RideResponseDto> requestRide(
            @Parameter(description = "Details of the ride to request")
            RequestedRideRequestDto rideDto
    );

    @Operation(summary = "Accept a ride", description = "Accept an incoming ride request")
    @PatchMapping("/{id}/accept")
    ResponseEntity<RideResponseDto> acceptRide(
            @Parameter(description = "ID of the ride to accept")
            @PathVariable String id,
            @Parameter(description = "Details of the accepted ride")
            AcceptedRideRequestDto rideDto
    );

    @Operation(summary = "Start a ride", description = "Start a ride that has been accepted")
    @PatchMapping("/{id}/start")
    ResponseEntity<RideResponseDto> startRide(
            @Parameter(description = "ID of the ride to start")
            String id
    );

    @Operation(summary = "Complete a ride", description = "Complete a ride that has been started")
    @PatchMapping("/{id}/complete")
    ResponseEntity<RideResponseDto> completeRide(
            @Parameter(description = "ID of the ride to complete")
            String id
    );

    @Operation(summary = "Cancel a ride", description = "Cancel a ride before it has been completed")
    @PatchMapping("/{id}/cancel")
    ResponseEntity<RideResponseDto> cancelRide(
            @Parameter(description = "ID of the ride to cancel")
            String id,
            @Parameter(description = "Details of the canceled ride")
            CancelledRideRequestDto rideDto
    );
}
