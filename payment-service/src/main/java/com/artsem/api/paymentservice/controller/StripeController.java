package com.artsem.api.paymentservice.controller;

import com.artsem.api.paymentservice.controller.api.StripeApi;
import com.artsem.api.paymentservice.model.dto.response.CapturePaymentResponseDto;
import com.artsem.api.paymentservice.model.dto.request.RefillBalanceRequestDto;
import com.artsem.api.paymentservice.model.dto.response.StripeResponseDto;
import com.artsem.api.paymentservice.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class StripeController implements StripeApi {

    private final StripeService stripeService;

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isPassengerAuthorizedForBalanceId(#refillBalanceRequestDto.balanceId(), authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PostMapping("/refill-balance")
    public ResponseEntity<StripeResponseDto> createBalanceRefillPayment(@RequestBody RefillBalanceRequestDto refillBalanceRequestDto) {
        StripeResponseDto stripeResponseDto = stripeService.createPayment(
                BigDecimal.valueOf(refillBalanceRequestDto.amount()),
                refillBalanceRequestDto.balanceId()
        );
        return ResponseEntity.ok(stripeResponseDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/verify")
    public ResponseEntity<CapturePaymentResponseDto> verify(@RequestParam String sessionId) {
        CapturePaymentResponseDto capturePaymentResponseDto = stripeService.capturePayment(sessionId);
        return ResponseEntity.ok(capturePaymentResponseDto);
    }

}