package com.artsem.api.paymentservice.service;

import com.artsem.api.paymentservice.model.dto.IsBalancePositiveDto;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;

import java.math.BigDecimal;

public interface BalanceService {

    BalanceResponseDto getById(Long id);

    BalanceResponseDto initBalance(Long userId);

    void delete(Long id);

    void refillBalance(BigDecimal amount, Long id);

    void validateBalanceExistence(Long balanceId);

    BalanceResponseDto charge(Long id, BigDecimal amount);

    IsBalancePositiveDto isBalancePositive(Long userId);
}
