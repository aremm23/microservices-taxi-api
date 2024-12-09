package com.artsem.api.reviewservice.service.impl;

import com.artsem.api.reviewservice.exceptions.EmptyIdsListException;
import com.artsem.api.reviewservice.exceptions.ReviewAlreadyExistException;
import com.artsem.api.reviewservice.exceptions.ReviewNotFoundException;
import com.artsem.api.reviewservice.feign.client.RideResponseDto;
import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.Review;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import com.artsem.api.reviewservice.repository.ReviewRepository;
import com.artsem.api.reviewservice.service.ReviewService;
import com.artsem.api.reviewservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ModelMapper mapper;

    private final RideService rideService;

    @Transactional(readOnly = true)
    @Override
    public Page<ReviewResponseDto> getList(ReviewFilter filter, Pageable pageable) {
        Specification<Review> spec = filter.toSpecification();
        Page<Review> reviews = reviewRepository.findAll(spec, pageable);
        return reviews.map(review -> mapper.map(review, ReviewResponseDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDto getById(Long id) {
        Review review = findReviewById(id);
        return mapper.map(review, ReviewResponseDto.class);
    }

    @Transactional
    @Override
    public ReviewResponseDto create(ReviewRequestDto reviewDto) {
        Review review = mapper.map(reviewDto, Review.class);
        checkIsReviewAlreadyExist(reviewDto);
        RideResponseDto rideResponseDto = rideService.getRideResponse(review.getRideId());
        rideService.validateRide(rideResponseDto, reviewDto);
        Review savedReview = reviewRepository.save(review);
        return mapper.map(savedReview, ReviewResponseDto.class);
    }

    private void checkIsReviewAlreadyExist(ReviewRequestDto reviewDto) {
        if (reviewRepository.existsByRideIdAndReviewType(
                reviewDto.getRideId(),
                reviewDto.getReviewType())
        ) {
            throw new ReviewAlreadyExistException();
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException();
        }
        reviewRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteMany(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new EmptyIdsListException();
        }
        List<Review> reviews = reviewRepository.findAllById(ids);
        if (reviews.size() != ids.size()) {
            throw new ReviewNotFoundException();
        }
        reviewRepository.deleteAll(reviews);
    }

    private Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                ReviewNotFoundException::new
        );
    }
}
