package com.artsem.api.driverservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @SequenceGenerator(
            name = "driverIdSeqGen",
            sequenceName = "driver_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "driverIdSeqGen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_free")
    private Boolean isFree;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @PrePersist
    private void initializeTimestamps() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

}