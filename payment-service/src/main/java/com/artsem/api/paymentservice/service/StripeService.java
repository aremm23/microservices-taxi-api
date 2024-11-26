package com.artsem.api.paymentservice.service;

import com.artsem.api.paymentservice.model.dto.response.CapturePaymentResponseDto;
import com.artsem.api.paymentservice.model.dto.response.StripeResponseDto;

import java.math.BigDecimal;

public interface StripeService {

    StripeResponseDto createPayment(BigDecimal amount, Long balanceId);

    CapturePaymentResponseDto capturePayment(String sessionId);

    void handleSuccessWebhook(String payload, String sigHeader);

}
