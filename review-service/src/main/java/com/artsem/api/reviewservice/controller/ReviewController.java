package com.artsem.api.reviewservice.controller;

import com.artsem.api.reviewservice.controller.api.ReviewApi;
import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import com.artsem.api.reviewservice.service.ReviewService;
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
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<PagedModel<ReviewResponseDto>> getList(
            @ModelAttribute ReviewFilter filter,
            Pageable pageable
    ) {
        Page<ReviewResponseDto> reviewResponseDtos = reviewService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(reviewResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(
            @PathVariable Long id
    ) {
        ReviewResponseDto review = reviewService.getById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(
            @Valid @RequestBody ReviewRequestDto reviewDto
    ) {
        ReviewResponseDto createdReview = reviewService.create(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @RequestParam List<Long> ids
    ) {
        reviewService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}