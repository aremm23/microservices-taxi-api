package com.artsem.api.passengerservice.controller.api;

import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerEmailResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
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
@Tag(name = "Passenger Controller", description = "API for managing passengers in the passenger service")
public interface PassengerApi {

    @Operation(summary = "Get a list of passengers", description = "Retrieve a paginated list of passengers with optional filters")
    ResponseEntity<PagedModel<PassengerResponseDto>> getList(
            @Parameter(description = "Filters for passenger search")
            PassengerFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    );

    @Operation(summary = "Get passenger details by ID", description = "Retrieve detailed information about a specific passenger")
    ResponseEntity<PassengerResponseDto> getOne(
            @Parameter(description = "ID of the passenger to retrieve")
            Long id
    );

    @Operation(summary = "Get passenger email by ID", description = "Retrieve email of a specific passenger")
    ResponseEntity<PassengerEmailResponseDto> getEmail(
            @Parameter(description = "ID of the passenger to retrieve email from")
            Long id
    );

    @Operation(summary = "Get multiple passengers by IDs", description = "Retrieve detailed information about multiple passengers")
    ResponseEntity<ListResponseDto<PassengerResponseDto>> getMany(
            @Parameter(description = "List of passenger IDs to retrieve")
            List<Long> ids
    );

    @Operation(summary = "Create a new passenger", description = "Create and register a new passenger in the system")
    ResponseEntity<PassengerResponseDto> create(
            @Parameter(description = "Details of the passenger to create")
            PassengerRequestDto passengerDto
    );

    @Operation(summary = "Update passenger details", description = "Update the information of an existing passenger")
    ResponseEntity<PassengerResponseDto> patch(
            @Parameter(description = "ID of the passenger to update")
            @PathVariable Long id,
            @Parameter(description = "Updated passenger details")
            PassengerUpdateRequestDto passengerDto
    );

    @Operation(summary = "Delete a passenger by ID", description = "Remove a passenger from the system by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the passenger to delete")
            Long id
    );

    @Operation(summary = "Delete multiple passengers by IDs", description = "Remove multiple passengers from the system by their IDs")
    ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of passenger IDs to delete")
            List<Long> ids
    );
}