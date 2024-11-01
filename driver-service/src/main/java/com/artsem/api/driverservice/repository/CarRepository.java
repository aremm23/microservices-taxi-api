package com.artsem.api.driverservice.repository;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Driver> {
    boolean existsByLicensePlate(String licensePlateNumber);

    @Query("SELECT car.id FROM Car car WHERE car.licensePlate = :licensePlate")
    Long findIdByLicensePlate(@Param("licensePlate") String licensePlate);
}