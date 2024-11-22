package com.artsem.api.paymentservice.service;

import java.math.BigDecimal;

public interface BalanceService {

    void refillBalance(BigDecimal amount, Long id);

    boolean isBalanceExist(Long balanceId);

}
