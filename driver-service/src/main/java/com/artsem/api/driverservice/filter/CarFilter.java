package com.artsem.api.driverservice.filter;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarCategory;
import org.springframework.data.jpa.domain.Specification;

public record CarFilter(String modelLike, CarCategory carCategory) {
    public Specification<Car> toSpecification() {
        return Specification.where(modelLikeSpec())
                .and(carCategorySpec());
    }

    private Specification<Car> modelLikeSpec() {
        return (root, query, cb) -> {
            if (modelLike != null && !modelLike.isEmpty()) {
                return cb.like(root.get("model"), "%" + modelLike + "%");
            }
            return null;
        };
    }

    private Specification<Car> carCategorySpec() {
        return (root, query, cb) -> {
            if (carCategory != null) {
                return cb.equal(root.get("carCategory"), carCategory);
            }
            return null;
        };
    }
}