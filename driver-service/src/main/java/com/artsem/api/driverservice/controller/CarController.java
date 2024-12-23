package com.artsem.api.driverservice.controller;

import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.service.CarService;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Car Controller", description = "API for managing cars in the driver service")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get a list of cars", description = "Retrieve a paginated list of cars with optional filters")
    @GetMapping
    public ResponseEntity<PagedModel<CarResponseDto>> getList(
            @Parameter(description = "Filters for car search")
            @ModelAttribute
            CarFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    ) {
        Page<CarResponseDto> carResponseDtos = carService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(carResponseDtos));
    }

    @Operation(summary = "Get car details by ID", description = "Retrieve detailed information about a specific car")
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getOne(
            @Parameter(description = "ID of the car to retrieve")
            @PathVariable
            Long id
    ) {
        CarResponseDto car = carService.getOne(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Get multiple cars by IDs", description = "Retrieve detailed information about multiple cars")
    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<CarResponseDto>> getMany(
            @Parameter(description = "List of car IDs to retrieve")
            @RequestParam
            List<Long> ids
    ) {
        ListResponseDto<CarResponseDto> cars = carService.getMany(ids);
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Create a new car", description = "Create and register a new car in the system")
    @PostMapping
    public ResponseEntity<CarResponseDto> create(
            @Parameter(description = "Details of the car to create")
            @Valid
            @RequestBody
            CarRequestDto carDto
    ) {
        CarResponseDto createdCar = carService.create(carDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @Operation(summary = "Update car details", description = "Update the information of an existing car")
    @PatchMapping("/{id}")
    public ResponseEntity<CarResponseDto> patch(
            @Parameter(description = "ID of the car to update")
            @PathVariable Long id,
            @Parameter(description = "Updated car details")
            @Valid
            @RequestBody
            CarUpdateRequestDto carDto
    ) {
        CarResponseDto updatedCar = carService.patch(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @Operation(summary = "Delete a car by ID", description = "Remove a car from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the car to delete")
            @PathVariable
            Long id
    ) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete multiple cars by IDs", description = "Remove multiple cars from the system by their IDs")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of car IDs to delete")
            @RequestParam
            List<Long> ids
    ) {
        carService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}

