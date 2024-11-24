package com.artsem.api.paymentservice.controller;

import com.artsem.api.paymentservice.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
public class WebHookController {

    public static final String STRIPE_SIGNATURE_HEADER = "Stripe-Signature";

    private final StripeService stripeService;

    @PostMapping("/success")
    public ResponseEntity<Void> handleWebhook(@RequestBody String payload, @RequestHeader(STRIPE_SIGNATURE_HEADER) String sigHeader) {
        stripeService.handleSuccessWebhook(payload, sigHeader);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}