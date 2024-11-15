package com.artsem.api.reviewservice.filter;

import com.artsem.api.reviewservice.model.Review;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record ReviewFilter(String rideIdLike, Long reviewedIdLike, Long reviewerIdLike) {
    public Specification<Review> toSpecification() {
        return Specification.where(rideIdLikeSpec())
                .and(passengerIdLikeSpec())
                .and(driverIdLikeSpec());
    }

    private Specification<Review> rideIdLikeSpec() {
        return (root, query, cb) -> {
            if (StringUtils.hasText(rideIdLike)) {
                return cb.like(root.get("rideId"), "%" + rideIdLike + "%");
            }
            return null;
        };
    }

    private Specification<Review> passengerIdLikeSpec() {
        return (root, query, cb) -> {
            if (reviewedIdLike != null) {
                return cb.equal(root.get("reviewedId"), reviewedIdLike);
            }
            return null;
        };
    }

    private Specification<Review> driverIdLikeSpec() {
        return (root, query, cb) -> {
            if (reviewerIdLike != null) {
                return cb.equal(root.get("reviewerId"), reviewerIdLike);
            }
            return null;
        };
    }
}