package com.artsem.api.reviewservice.controller.api;

import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Review Controller", description = "API for managing reviews in the review service")
public interface ReviewApi {

    @Operation(summary = "Get a list of reviews", description = "Retrieve a paginated list of reviews with optional filters")
    ResponseEntity<PagedModel<ReviewResponseDto>> getList(
            @Parameter(description = "Filters for review search") ReviewFilter filter,
            @Parameter(description = "Pagination information") Pageable pageable
    );

    @Operation(summary = "Get review details by ID", description = "Retrieve detailed information about a specific review")
    ResponseEntity<ReviewResponseDto> getById(
            @Parameter(description = "ID of the review to retrieve") Long id
    );

    @Operation(summary = "Create a new review", description = "Create and save a new review in the system")
    ResponseEntity<ReviewResponseDto> create(
            @Parameter(description = "Details of the review to create") ReviewRequestDto reviewDto
    );

    @Operation(summary = "Delete a review by ID", description = "Remove a review from the system by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the review to delete") Long id
    );

    @Operation(summary = "Delete multiple reviews by IDs", description = "Remove multiple reviews from the system by their IDs")
    ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of review IDs to delete") List<Long> ids
    );
}