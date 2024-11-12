package com.artsem.api.passengerservice.filter;

import com.artsem.api.passengerservice.model.Passenger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record PassengerFilter(String surnameLike) {
    public Specification<Passenger> toSpecification() {
        return Specification.where(surnameLikeSpec());
    }

    private Specification<Passenger> surnameLikeSpec() {
        return (root, query, cb) -> {
            if (StringUtils.hasText(surnameLike)) {
                return cb.like(root.get("surname"), "%" + surnameLike + "%");
            }
            return null;
        };
    }
}
