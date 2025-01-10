package com.artsem.api.reviewservice.controller;

import com.artsem.api.reviewservice.controller.api.RatingApi;
import com.artsem.api.reviewservice.model.dto.response.RatingResponseDto;
import com.artsem.api.reviewservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController implements RatingApi {

    private final RatingService ratingService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DRIVER', 'ROLE_PASSENGER')")
    @GetMapping("/driver/{id}")
    public ResponseEntity<RatingResponseDto> getDriverRating(@PathVariable Long id) {
        RatingResponseDto ratingResponseDto = ratingService.getDriverRating(id);
        return ResponseEntity.ok(ratingResponseDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DRIVER', 'ROLE_PASSENGER')")
    @GetMapping("/passenger/{id}")
    public ResponseEntity<RatingResponseDto> getPassengerRating(@PathVariable Long id) {
        RatingResponseDto ratingResponseDto = ratingService.getPassengerRating(id);
        return ResponseEntity.ok(ratingResponseDto);
    }
}
