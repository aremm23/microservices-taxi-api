package com.artsem.api.ridesservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "rides")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private RideTariff tariffId;
    private PaymentMethod paymentMethodId;

    private LocalDateTime requestTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime cancelTime;

    private RideStatus statusId;
}
