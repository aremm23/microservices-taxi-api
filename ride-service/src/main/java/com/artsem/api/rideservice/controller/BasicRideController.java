package com.artsem.api.rideservice.controller;

import com.artsem.api.rideservice.filter.RideFilter;
import com.artsem.api.rideservice.model.dto.rest.request.RideRequestDto;
import com.artsem.api.rideservice.model.dto.rest.response.RideResponseDto;
import com.artsem.api.rideservice.service.RideBasicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
@Tag(name = "Ride Controller", description = "API for managing rides in the ride service")
public class BasicRideController {

    private final RideBasicService rideBasicService;

    @Operation(summary = "Get a list of rides", description = "Retrieve a paginated list of rides with optional filters")
    @GetMapping
    public ResponseEntity<PagedModel<RideResponseDto>> getList(
            @Parameter(description = "Filters for ride search")
            @ModelAttribute
            RideFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    ) {
        Page<RideResponseDto> rideResponseDtos = rideBasicService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(rideResponseDtos));
    }

    @Operation(summary = "Get ride details by ID", description = "Retrieve detailed information about a specific ride")
    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getById(
            @Parameter(description = "ID of the ride to retrieve")
            @PathVariable
            String id
    ) {
        RideResponseDto ride = rideBasicService.getById(id);
        return ResponseEntity.ok(ride);
    }

    @Operation(summary = "Create a new ride", description = "Create and register a new ride in the system")
    @PostMapping
    public ResponseEntity<RideResponseDto> create(
            @Parameter(description = "Details of the ride to create")
            @Valid
            @RequestBody
            RideRequestDto rideDto
    ) {
        RideResponseDto createdRide = rideBasicService.create(rideDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRide);
    }

    @Operation(summary = "Delete a ride by ID", description = "Remove a ride from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the ride to delete")
            @PathVariable
            String id
    ) {
        rideBasicService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}