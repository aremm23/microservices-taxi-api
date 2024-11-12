package com.artsem.api.passengerservice.controller;

import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.service.PassengerService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
@Tag(name = "Passenger Controller", description = "API for managing passengers in the passenger service")
public class PassengerController {

    private final PassengerService passengerService;

    @Operation(summary = "Get a list of passengers", description = "Retrieve a paginated list of passengers with optional filters")
    @GetMapping
    public ResponseEntity<PagedModel<PassengerResponseDto>> getList(
            @Parameter(description = "Filters for passenger search")
            @ModelAttribute
            PassengerFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    ) {
        Page<PassengerResponseDto> passengerResponseDtos = passengerService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(passengerResponseDtos));
    }

    @Operation(summary = "Get passenger details by ID", description = "Retrieve detailed information about a specific passenger")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getOne(
            @Parameter(description = "ID of the passenger to retrieve")
            @PathVariable
            Long id
    ) {
        PassengerResponseDto passenger = passengerService.getOne(id);
        return ResponseEntity.ok(passenger);
    }

    @Operation(summary = "Get multiple passengers by IDs", description = "Retrieve detailed information about multiple passengers")
    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<PassengerResponseDto>> getMany(
            @Parameter(description = "List of passenger IDs to retrieve")
            @RequestParam
            List<Long> ids
    ) {
        ListResponseDto<PassengerResponseDto> passengers = passengerService.getMany(ids);
        return ResponseEntity.ok(passengers);
    }

    @Operation(summary = "Create a new passenger", description = "Create and register a new passenger in the system")
    @PostMapping
    public ResponseEntity<PassengerResponseDto> create(
            @Parameter(description = "Details of the passenger to create")
            @Valid
            @RequestBody
            PassengerRequestDto passengerDto
    ) {
        PassengerResponseDto createdPassenger = passengerService.create(passengerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    @Operation(summary = "Update passenger details", description = "Update the information of an existing passenger")
    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> patch(
            @Parameter(description = "ID of the passenger to update")
            @PathVariable
            Long id,
            @Parameter(description = "Updated passenger details")
            @Valid
            @RequestBody
            PassengerUpdateRequestDto passengerDto
    ) {
        PassengerResponseDto updatedPassenger = passengerService.patch(id, passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @Operation(summary = "Delete a passenger by ID", description = "Remove a passenger from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the passenger to delete")
            @PathVariable
            Long id
    ) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete multiple passengers by IDs", description = "Remove multiple passengers from the system by their IDs")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of passenger IDs to delete")
            @RequestParam
            List<Long> ids
    ) {
        passengerService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}