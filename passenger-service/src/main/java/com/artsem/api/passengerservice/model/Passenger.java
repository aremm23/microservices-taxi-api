package com.artsem.api.passengerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "passenger")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @SequenceGenerator(
            name = "passengerIdSeqGen",
            sequenceName = "passenger_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "passengerIdSeqGen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

}