package com.artsem.api.reviewservice.controller;

import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import com.artsem.api.reviewservice.service.ReviewService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "API for managing reviews in the review service")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Get a list of reviews", description = "Retrieve a paginated list of reviews with optional filters")
    @GetMapping
    public ResponseEntity<PagedModel<ReviewResponseDto>> getList(
            @Parameter(description = "Filters for review search")
            @ModelAttribute ReviewFilter filter,
            @Parameter(description = "Pagination information")
            Pageable pageable
    ) {
        Page<ReviewResponseDto> reviewResponseDtos = reviewService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(reviewResponseDtos));
    }

    @Operation(summary = "Get review details by ID", description = "Retrieve detailed information about a specific review")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(
            @Parameter(description = "ID of the review to retrieve")
            @PathVariable Long id
    ) {
        ReviewResponseDto review = reviewService.getById(id);
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "Create a new review", description = "Create and save a new review in the system")
    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(
            @Parameter(description = "Details of the review to create")
            @Valid @RequestBody ReviewRequestDto reviewDto
    ) {
        ReviewResponseDto createdReview = reviewService.create(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @Operation(summary = "Delete a review by ID", description = "Remove a review from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the review to delete")
            @PathVariable Long id
    ) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete multiple reviews by IDs", description = "Remove multiple reviews from the system by their IDs")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @Parameter(description = "List of review IDs to delete")
            @RequestParam List<Long> ids
    ) {
        reviewService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}