package com.artsem.api.driverservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

}