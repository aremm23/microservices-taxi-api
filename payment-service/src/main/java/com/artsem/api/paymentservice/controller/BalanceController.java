package com.artsem.api.paymentservice.controller;

import com.artsem.api.paymentservice.controller.api.BalanceApi;
import com.artsem.api.paymentservice.model.dto.request.AmountRequestDto;
import com.artsem.api.paymentservice.model.dto.request.InitBalanceRequestDto;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;
import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/balances")
@RequiredArgsConstructor
public class BalanceController implements BalanceApi {

    private final BalanceService balanceService;

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#initBalanceRequestDto.userId(), authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PostMapping("/init")
    public ResponseEntity<BalanceResponseDto> initBalance(
            @RequestBody InitBalanceRequestDto initBalanceRequestDto
    ) {
        BalanceResponseDto savedBalance = balanceService.initBalance(initBalanceRequestDto.userId());
        return new ResponseEntity<>(savedBalance, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/charge")
    public ResponseEntity<BalanceResponseDto> charge(
            @PathVariable Long id,
            @RequestBody AmountRequestDto amountRequestDto
    ) {
        BalanceResponseDto savedBalance = balanceService.charge(
                id,
                BigDecimal.valueOf(amountRequestDto.amount())
        );
        return ResponseEntity.ok(savedBalance);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isPassengerAuthorizedForBalanceId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @GetMapping("/{id}")
    public ResponseEntity<BalanceResponseDto> getById(
            @PathVariable Long id
    ) {
        BalanceResponseDto balance = balanceService.getById(id);
        return ResponseEntity.ok(balance);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isPassengerAuthorizedForBalanceId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        balanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}