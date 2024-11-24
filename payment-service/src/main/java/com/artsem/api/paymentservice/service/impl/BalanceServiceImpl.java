package com.artsem.api.paymentservice.service.impl;

import com.artsem.api.paymentservice.model.Balance;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;
import com.artsem.api.paymentservice.repository.BalanceRepository;
import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public BalanceResponseDto getById(Long id) {
        Balance balance = findBalanceById(id);
        return mapper.map(balance, BalanceResponseDto.class);
    }

    @Transactional
    @Override
    public BalanceResponseDto initBalance(Long userId) {
        checkIsExistByUser(userId);
        Balance balance = Balance.builder()
                .userId(userId)
                .amount(BigDecimal.ZERO)
                .build();
        Balance savedBalance = balanceRepository.save(balance);
        return mapper.map(savedBalance, BalanceResponseDto.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        checkIsExist(id);
        balanceRepository.deleteById(id);
    }

    private void checkIsExist(Long id) {
        if (!isBalanceExist(id)) {
            throw new RuntimeException("Balance not found");
        }
    }

    private void checkIsExistByUser(Long userId) {
        if (balanceRepository.existsByUserId(userId)) {
            throw new RuntimeException("Balance already exists for user: " + userId);
        }
    }

    @Transactional
    @Override
    public void refillBalance(BigDecimal amount, Long id) {
        Balance balance = findBalanceById(id);
        balance.setAmount(balance.getAmount().add(amount));
        balanceRepository.save(balance);
    }

    private Balance findBalanceById(Long id) {
        return balanceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Not found balance with id: " + id)//TODO: custom exception
        );
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isBalanceExist(Long id) {
        return balanceRepository.existsById(id);
    }
}
