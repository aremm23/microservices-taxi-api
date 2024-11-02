package com.artsem.api.driverservice.filter;

import com.artsem.api.driverservice.model.Driver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record DriverFilter(String surnameLike, Boolean isFree) {
    public Specification<Driver> toSpecification() {
        return Specification.where(surnameLikeSpec()).and(isFreeSpec());
    }

    private Specification<Driver> surnameLikeSpec() {
        return (root, query, cb) -> {
            if (StringUtils.hasText(surnameLike)) {
                return cb.like(root.get("surname"), "%" + surnameLike + "%");
            }
            return null;
        };
    }

    private Specification<Driver> isFreeSpec() {
        return (root, query, cb) -> {
            if (isFree != null) {
                return cb.equal(root.get("isFree"), isFree);
            }
            return null;
        };
    }
}