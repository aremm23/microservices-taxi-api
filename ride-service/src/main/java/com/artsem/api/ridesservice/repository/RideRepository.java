package com.artsem.api.ridesservice.repository;

import com.artsem.api.rideservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, String> {
}