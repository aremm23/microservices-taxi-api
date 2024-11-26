package com.artsem.api.paymentservice.controller;

import com.artsem.api.paymentservice.model.dto.request.InitBalanceRequestDto;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;
import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/init")
    public ResponseEntity<BalanceResponseDto> initBalance(
            @RequestBody InitBalanceRequestDto initBalanceRequestDto
    ) {
        BalanceResponseDto savedBalance = balanceService.initBalance(initBalanceRequestDto.userId());
        return new ResponseEntity<>(savedBalance, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BalanceResponseDto> getById(
            @PathVariable Long id
    ) {
        BalanceResponseDto balance = balanceService.getById(id);
        return ResponseEntity.ok(balance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        balanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}