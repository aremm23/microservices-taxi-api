package com.artsem.api.driverservice.controller.api;

import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.response.CarResponseDto;
import com.artsem.api.driverservice.model.dto.response.ListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Car Controller", description = "API for managing cars in the driver service")
public interface CarApi {

    @Operation(summary = "Get a list of cars", description = "Retrieve a paginated list of cars with optional filters")
    ResponseEntity<PagedModel<CarResponseDto>> getList(
            @Parameter(description = "Filters for car search") CarFilter filter,
            @Parameter(description = "Pagination information") Pageable pageable
    );

    @Operation(summary = "Get car details by ID", description = "Retrieve detailed information about a specific car")
    ResponseEntity<CarResponseDto> getOne(
            @Parameter(description = "ID of the car to retrieve") Long id
    );

    @Operation(summary = "Get multiple cars by IDs", description = "Retrieve detailed information about multiple cars")
    ResponseEntity<ListResponseDto<CarResponseDto>> getMany(
            @Parameter(description = "List of car IDs to retrieve") List<Long> ids
    );

    @Operation(summary = "Create a new car", description = "Create and register a new car in the system")
    ResponseEntity<CarResponseDto> create(
            @Parameter(description = "Details of the car to create") CarRequestDto carDto
    );

    @Operation(summary = "Update car details", description = "Update the information of an existing car")
    ResponseEntity<CarResponseDto> patch(
            @Parameter(description = "ID of the car to update") Long id,
            @Parameter(description = "Updated car details") CarUpdateRequestDto carDto
    );

    @Operation(summary = "Delete a car by ID", description = "Remove a car from the system by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the car to delete") Long id
    );

    @Operation(summary = "Delete multiple cars by IDs", description = "Remove multiple cars from the system by their IDs")
    ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of car IDs to delete") List<Long> ids
    );
}
