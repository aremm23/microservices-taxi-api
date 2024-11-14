package com.artsem.api.reviewservice.repository;

import com.artsem.api.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}