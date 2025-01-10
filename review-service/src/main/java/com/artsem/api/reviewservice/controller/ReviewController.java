package com.artsem.api.reviewservice.controller;

import com.artsem.api.reviewservice.controller.api.ReviewApi;
import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import com.artsem.api.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<ReviewResponseDto>> getList(
            @ModelAttribute ReviewFilter filter,
            Pageable pageable
    ) {
        Page<ReviewResponseDto> reviewResponseDtos = reviewService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(reviewResponseDtos));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PASSENGER', 'ROLE_DRIVER')")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(
            @PathVariable Long id
    ) {
        ReviewResponseDto review = reviewService.getById(id);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("""
            (hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER') &&
            @userAccessValidator.isReviewerAuthorizeForReview(#reviewDto.reviewerId, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(
            @Valid @RequestBody ReviewRequestDto reviewDto
    ) {
        ReviewResponseDto createdReview = reviewService.create(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(
            @RequestParam List<Long> ids
    ) {
        reviewService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}
