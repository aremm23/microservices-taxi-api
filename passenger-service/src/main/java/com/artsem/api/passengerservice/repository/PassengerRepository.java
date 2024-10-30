package com.artsem.api.passengerservice.repository;

import com.artsem.api.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PassengerRepository extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {
    boolean existsByEmail(String email);

    @Query("SELECT p.id FROM Passenger p WHERE p.email = :email")
    Long findIdByEmail(@Param("email") String email);
}