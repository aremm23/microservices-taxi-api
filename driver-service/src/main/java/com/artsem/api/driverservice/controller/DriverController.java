package com.artsem.api.driverservice.controller;

import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.service.DriverService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Tag(name = "Driver Controller", description = "API for managing drivers in the driver service")
public class DriverController {

    private final DriverService driverService;

    @Operation(summary = "Get a list of drivers", description = "Retrieve a paginated list of drivers with optional filters")
    @GetMapping
    public ResponseEntity<PagedModel<DriverResponseDto>> getList(
            @Parameter(description = "Filters for driver search")
            @ModelAttribute
            DriverFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    ) {
        Page<DriverResponseDto> driverResponseDtos = driverService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(driverResponseDtos));
    }

    @Operation(summary = "Get driver details by ID", description = "Retrieve detailed information about a specific driver")
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getOne(
            @Parameter(description = "ID of the driver to retrieve")
            @PathVariable
            Long id
    ) {
        DriverResponseDto driver = driverService.getOne(id);
        return ResponseEntity.ok(driver);
    }

    @Operation(summary = "Get driver details with car by ID", description = "Retrieve detailed information about a driver along with assigned car")
    @GetMapping("/{id}/car")
    public ResponseEntity<DriverAndCarResponseDto> getOneWithCar(
            @Parameter(description = "ID of the driver to retrieve")
            @PathVariable
            Long id
    ) {
        DriverAndCarResponseDto driver = driverService.getOneWithCar(id);
        return ResponseEntity.ok(driver);
    }

    @Operation(summary = "Get multiple drivers by IDs", description = "Retrieve detailed information about multiple drivers")
    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<DriverResponseDto>> getMany(
            @Parameter(description = "List of driver IDs to retrieve")
            @RequestParam
            List<Long> ids
    ) {
        ListResponseDto<DriverResponseDto> drivers = driverService.getMany(ids);
        return ResponseEntity.ok(drivers);
    }

    @Operation(summary = "Create a new driver", description = "Create and register a new driver in the system")
    @PostMapping
    public ResponseEntity<DriverResponseDto> create(
            @Parameter(description = "Details of the driver to create")
            @Valid
            @RequestBody
            DriverRequestDto driverDto
    ) {
        DriverResponseDto createdDriver = driverService.create(driverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDriver);
    }

    @Operation(summary = "Update driver details", description = "Update the information of an existing driver")
    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponseDto> patch(
            @Parameter(description = "ID of the driver to update")
            @PathVariable
            Long id,
            @Parameter(description = "Updated driver details")
            @Valid
            @RequestBody
            DriverUpdateRequestDto driverDto
    ) {
        DriverResponseDto updatedDriver = driverService.patch(id, driverDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @Operation(summary = "Delete a driver by ID", description = "Remove a driver from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the driver to delete")
            @PathVariable
            Long id
    ) {
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete multiple drivers by IDs", description = "Remove multiple drivers from the system by their IDs")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of driver IDs to delete")
            @RequestParam
            List<Long> ids
    ) {
        driverService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update driver status", description = "Update the status of an existing driver")
    @PatchMapping("/{id}/status")
    public ResponseEntity<DriverResponseDto> updateDriverStatus(
            @Parameter(description = "ID of the driver to update")
            @PathVariable
            Long id,
            @Parameter(description = "New status for the driver")
            @RequestBody
            DriverStatusUpdateRequestDto statusUpdateDto
    ) {
        DriverResponseDto updatedDriver = driverService.updateDriverStatus(id, statusUpdateDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @Operation(summary = "Assign a car to a driver", description = "Assign an existing car to a driver")
    @PatchMapping("/{driverId}/car/{carId}")
    public ResponseEntity<DriverAndCarResponseDto> assignCarToDriver(
            @Parameter(description = "ID of the driver")
            @PathVariable
            Long driverId,
            @Parameter(description = "ID of the car to assign")
            @PathVariable
            Long carId
    ) {
        DriverAndCarResponseDto updatedDriver = driverService.assignCarToDriver(driverId, carId);
        return ResponseEntity.ok(updatedDriver);
    }

    @Operation(summary = "Remove a car from a driver", description = "Unassign the currently assigned car from a driver")
    @DeleteMapping("/{driverId}/car")
    public ResponseEntity<DriverAndCarResponseDto> removeCarFromDriver(
            @Parameter(description = "ID of the driver")
            @PathVariable
            Long driverId
    ) {
        DriverAndCarResponseDto updatedDriver = driverService.removeCarFromDriver(driverId);
        return ResponseEntity.ok(updatedDriver);
    }
}