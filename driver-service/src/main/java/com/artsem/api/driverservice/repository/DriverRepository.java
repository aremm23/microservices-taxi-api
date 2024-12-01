package com.artsem.api.driverservice.repository;

import com.artsem.api.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
    boolean existsByEmail(String email);

    @Query("SELECT d.id FROM Driver d WHERE d.email = :email")
    Long findIdByEmail(@Param("email") String email);

    @Query("SELECT d.email FROM Driver d WHERE d.id = :id")
    Optional<String> findEmailById(@Param("id") Long id);
}