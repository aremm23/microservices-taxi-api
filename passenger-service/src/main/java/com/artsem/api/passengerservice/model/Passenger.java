package com.artsem.api.passengerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Passenger passenger = (Passenger) o;
        return getId() != null && Objects.equals(getId(), passenger.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}