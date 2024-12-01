package com.artsem.api.driverservice.controller.api;

import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.response.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverEmailResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverStatusResponseDto;
import com.artsem.api.driverservice.model.dto.response.ListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Driver Controller", description = "API for managing drivers in the driver service")
public interface DriverApi {

    @Operation(summary = "Get a list of drivers", description = "Retrieve a paginated list of drivers with optional filters")
    ResponseEntity<PagedModel<DriverResponseDto>> getList(
            @Parameter(description = "Filters for driver search") DriverFilter filter,
            @Parameter(description = "Pagination information") Pageable pageable
    );

    @Operation(summary = "Get driver details by ID", description = "Retrieve detailed information about a specific driver")
    ResponseEntity<DriverResponseDto> getOne(
            @Parameter(description = "ID of the driver to retrieve") Long id
    );

    @Operation(summary = "Get driver email by ID", description = "Retrieve email of a specific driver")
    ResponseEntity<DriverEmailResponseDto> getEmailById(
            @Parameter(description = "ID of the driver to retrieve email from") Long id
    );

    @Operation(summary = "Get driver details with car by ID", description = "Retrieve detailed information about a driver along with assigned car")
    ResponseEntity<DriverAndCarResponseDto> getOneWithCar(
            @Parameter(description = "ID of the driver to retrieve") Long id
    );

    @Operation(summary = "Get driver status details by ID", description = "Retrieve detailed information about driver status")
    ResponseEntity<DriverStatusResponseDto> getDriverStatus(
            @Parameter(description = "ID of the driver to retrieve") Long id
    );

    @Operation(summary = "Get multiple drivers by IDs", description = "Retrieve detailed information about multiple drivers")
    ResponseEntity<ListResponseDto<DriverResponseDto>> getMany(
            @Parameter(description = "List of driver IDs to retrieve") List<Long> ids
    );

    @Operation(summary = "Create a new driver", description = "Create and register a new driver in the system")
    ResponseEntity<DriverResponseDto> create(
            @Parameter(description = "Details of the driver to create") DriverRequestDto driverDto
    );

    @Operation(summary = "Update driver details", description = "Update the information of an existing driver")
    ResponseEntity<DriverResponseDto> patch(
            @Parameter(description = "ID of the driver to update") Long id,
            @Parameter(description = "Updated driver details") DriverUpdateRequestDto driverDto
    );

    @Operation(summary = "Delete a driver by ID", description = "Remove a driver from the system by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the driver to delete") Long id
    );

    @Operation(summary = "Delete multiple drivers by IDs", description = "Remove multiple drivers from the system by their IDs")
    ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of driver IDs to delete") List<Long> ids
    );

    @Operation(summary = "Update driver status", description = "Update the status of an existing driver")
    ResponseEntity<DriverResponseDto> updateDriverStatus(
            @Parameter(description = "ID of the driver to update") Long id,
            @Parameter(description = "New status for the driver") DriverStatusUpdateRequestDto statusUpdateDto
    );

    @Operation(summary = "Assign a car to a driver", description = "Assign an existing car to a driver")
    ResponseEntity<DriverAndCarResponseDto> assignCarToDriver(
            @Parameter(description = "ID of the driver") Long driverId,
            @Parameter(description = "ID of the car to assign") Long carId
    );

    @Operation(summary = "Remove a car from a driver", description = "Unassign the currently assigned car from a driver")
    ResponseEntity<DriverAndCarResponseDto> removeCarFromDriver(
            @Parameter(description = "ID of the driver") Long driverId
    );
}
