package com.artsem.api.passengerservice.repository;

import com.artsem.api.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PassengerRepository extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {
    boolean existsByEmailOrPhone(String email, String phone);
}