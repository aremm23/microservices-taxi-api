package com.artsem.api.paymentservice.model;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Table(name = "balance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Balance {
    @Id
    @SequenceGenerator(
            name = "balanceIdSeqGen",
            sequenceName = "balance_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(generator = "balanceIdSeqGen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", precision = 8, scale = 3, nullable = false)
    private BigDecimal amount;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}