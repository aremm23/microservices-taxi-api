package com.artsem.api.rideservice.repository;

import com.artsem.api.rideservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, String> {
}