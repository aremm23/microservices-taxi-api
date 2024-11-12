package com.artsem.api.rideservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "rides")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Ride {
    @Id
    private String id;

    private Long passengerId;
    private String pickUpLocation;
    private String dropOffLocation;

    private Long driverId;
    private Long carId;
    private String carLicensePlate;

    private Double distance;
    private BigDecimal price;
    private Integer tariffId;
    private Integer paymentMethodId;

    private LocalDateTime requestedTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;
    private LocalDateTime canceledTime;

    private Integer statusId;
}