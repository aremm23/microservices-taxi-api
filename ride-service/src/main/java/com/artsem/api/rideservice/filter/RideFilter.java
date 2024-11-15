package com.artsem.api.rideservice.filter;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public record RideFilter(
        Long passengerId,
        Long driverId,
        Long statusId,
        Long carId,
        Long paymentMethodId
) {
    public Query toQuery() {
        Query query = new Query();
        addCriteria(query, "passengerId", passengerId);
        addCriteria(query, "driverId", driverId);
        addCriteria(query, "statusId", statusId);
        addCriteria(query, "carId", carId);
        addCriteria(query, "paymentMethodId", paymentMethodId);
        return query;
    }

    private void addCriteria(Query query, String field, Long value) {
        if (value != null) {
            query.addCriteria(Criteria.where(field).is(value));
        }
    }
}