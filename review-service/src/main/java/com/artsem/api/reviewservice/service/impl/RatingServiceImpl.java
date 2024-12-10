package com.artsem.api.reviewservice.service.impl;

import com.artsem.api.reviewservice.model.ReviewType;
import com.artsem.api.reviewservice.model.dto.response.RatingResponseDto;
import com.artsem.api.reviewservice.repository.ReviewRepository;
import com.artsem.api.reviewservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public RatingResponseDto getPassengerRating(Long passengerId) {
        double averageRate = reviewRepository.findAverageRateByReviewedIdAndReviewType(passengerId, ReviewType.PASSENGER);
        return RatingResponseDto.builder()
                .rate(averageRate)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponseDto getDriverRating(Long driverId) {
        double averageRate = reviewRepository.findAverageRateByReviewedIdAndReviewType(driverId, ReviewType.DRIVER);
        return RatingResponseDto.builder()
                .rate(averageRate)
                .build();
    }
}
