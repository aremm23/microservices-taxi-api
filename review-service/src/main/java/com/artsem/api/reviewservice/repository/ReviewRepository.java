package com.artsem.api.reviewservice.repository;

import com.artsem.api.reviewservice.model.Review;
import com.artsem.api.reviewservice.model.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.rideId = :rideId AND r.reviewType = :reviewType")
    boolean existsByRideIdAndReviewType(String rideId, ReviewType reviewType);

}