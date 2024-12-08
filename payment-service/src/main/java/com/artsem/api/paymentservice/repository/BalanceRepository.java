package com.artsem.api.paymentservice.repository;

import com.artsem.api.paymentservice.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    boolean existsByUserId(Long userId);

    Optional<Balance> getByUserId(Long userId);

    @Query("SELECT CASE WHEN b.amount > 0 THEN true ELSE false END FROM Balance b WHERE b.userId = :userId")
    Optional<Boolean> isBalancePositiveByUserId(@Param("userId") Long userId);
}