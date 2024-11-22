package com.artsem.api.paymentservice.service.impl;

import com.artsem.api.paymentservice.service.BalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceImplMock implements BalanceService {
    @Override
    public void refillBalance(BigDecimal amount, Long id) {
        //TODO To be implemented
    }

    @Override
    public boolean isBalanceExist(Long balanceId) {
        return true;
        //TODO To be implemented
    }
}
