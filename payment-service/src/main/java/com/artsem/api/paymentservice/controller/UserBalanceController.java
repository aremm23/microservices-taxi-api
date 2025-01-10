package com.artsem.api.paymentservice.controller;

import com.artsem.api.paymentservice.controller.api.UserBalanceApi;
import com.artsem.api.paymentservice.model.dto.IsBalancePositiveDto;
import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserBalanceController implements UserBalanceApi {

    private final BalanceService balanceService;

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#userId, authentication)) ||
            @userAccessValidator.isClientAuthorizedForRequest('ride-service', authentication) ||
            hasRole('ROLE_ADMIN')
            """)
    @GetMapping("/{userId}/balance/is-positive")
    public ResponseEntity<IsBalancePositiveDto> isBalancePositive(@PathVariable Long userId) {
        IsBalancePositiveDto response = balanceService.isBalancePositive(userId);
        return ResponseEntity.ok(response);
    }

}
