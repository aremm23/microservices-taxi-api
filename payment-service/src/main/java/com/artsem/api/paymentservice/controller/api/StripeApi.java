package com.artsem.api.paymentservice.controller.api;

import com.artsem.api.paymentservice.model.dto.request.RefillBalanceRequestDto;
import com.artsem.api.paymentservice.model.dto.response.CapturePaymentResponseDto;
import com.artsem.api.paymentservice.model.dto.response.StripeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Stripe Controller", description = "API for managing payments via Stripe in the payment service")
public interface StripeApi {

    @Operation(summary = "Refill balance via Stripe", description = "Create a payment to refill the user’s balance via Stripe")
    ResponseEntity<StripeResponseDto> createBalanceRefillPayment(
            @Parameter(description = "Request details for balance refill payment")
            RefillBalanceRequestDto refillBalanceRequestDto
    );

    @Operation(summary = "Verify payment via Stripe", description = "Verify a Stripe payment using the session ID")
    ResponseEntity<CapturePaymentResponseDto> verify(
            @Parameter(description = "Session ID of the Stripe payment")
            String sessionId
    );
}
