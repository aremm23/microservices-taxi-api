package com.artsem.api.paymentservice.repository;

import com.artsem.api.paymentservice.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    boolean existsByUserId(Long userId);
}