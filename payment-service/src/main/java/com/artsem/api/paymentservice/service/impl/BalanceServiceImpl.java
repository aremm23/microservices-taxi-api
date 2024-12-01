package com.artsem.api.paymentservice.service.impl;

import com.artsem.api.paymentservice.exceptions.BalanceAlreadyExistsException;
import com.artsem.api.paymentservice.exceptions.BalanceNotFoundException;
import com.artsem.api.paymentservice.model.Balance;
import com.artsem.api.paymentservice.model.dto.IsBalancePositiveDto;
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

    @Transactional
    @Override
    public BalanceResponseDto charge(Long id, BigDecimal amount) {
        checkIsExist(id);
        Balance balance = findBalanceById(id);
        balance.setAmount(balance.getAmount().subtract(amount));
        Balance savedBalance = balanceRepository.save(balance);
        return mapper.map(savedBalance, BalanceResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public IsBalancePositiveDto isBalancePositive(Long userId) {
        boolean isBalancePositive = balanceRepository.isBalancePositiveByUserId(userId).orElseThrow(
                BalanceNotFoundException::new
        );
        return IsBalancePositiveDto.builder()
                .balanceUserId(userId)
                .isBalancePositive(isBalancePositive)
                .build();
    }

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
        if (!balanceRepository.existsById(id)) {
            throw new BalanceNotFoundException();
        }
    }

    private void checkIsExistByUser(Long userId) {
        if (balanceRepository.existsByUserId(userId)) {
            throw new BalanceAlreadyExistsException();
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
                BalanceNotFoundException::new
        );
    }

    @Transactional(readOnly = true)
    @Override
    public void isBalanceExist(Long id) {
        checkIsExist(id);
    }
}
