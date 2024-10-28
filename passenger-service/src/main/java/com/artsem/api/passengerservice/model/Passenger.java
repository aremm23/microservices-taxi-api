package com.artsem.api.passengerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passenger")
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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "phone", unique = true)
    private String phone;

}