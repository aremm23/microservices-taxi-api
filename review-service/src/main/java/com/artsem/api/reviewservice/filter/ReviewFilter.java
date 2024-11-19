package com.artsem.api.reviewservice.filter;

import com.artsem.api.reviewservice.model.Review;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record ReviewFilter(String rideIdLike, Long reviewedIdLike, Long reviewerIdLike) {
    public Specification<Review> toSpecification() {
        return Specification.where(rideIdLikeSpec())
                .and(equalSpecification("reviewedId", reviewedIdLike))
                .and(equalSpecification("reviewerId", reviewerIdLike));
    }

    private Specification<Review> rideIdLikeSpec() {
        return (root, query, cb) -> {
            if (StringUtils.hasText(rideIdLike)) {
                return cb.like(root.get("rideId"), "%" + rideIdLike + "%");
            }
            return null;
        };
    }

    private Specification<Review> equalSpecification(String fieldName, Long value) {
        return (root, query, cb) -> {
            if (value != null) {
                return cb.equal(root.get(fieldName), value);
            }
            return null;
        };
    }
}