package com.artsem.api.paymentservice.service.impl;

import com.artsem.api.paymentservice.model.dto.response.CapturePaymentResponseDto;
import com.artsem.api.paymentservice.model.dto.response.StripeResponseDto;
import com.artsem.api.paymentservice.service.BalanceService;
import com.artsem.api.paymentservice.service.StripeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StripeServiceImpl implements StripeService {

    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String BALANCE_TOP_UP = "Balance top-up";
    public static final String CURRENCY = "byn";
    public static final int COINS = 100;
    public static final long QUANTITY = 1L;
    public static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";
    public static final String BALANCE_ID = "balanceId";
    private final String secretKey;
    private final String webhookSuccessSecretKey;
    private final String successUrl;
    private final String cancelUrl;

    private final BalanceService balanceService;

    public StripeServiceImpl(
            @Value("${stripe.secretKey}") String secretKey,
            @Value("${stripe.webhook.successSecretKey}") String webhookSuccessSecretKey,
            @Value("${stripe.success-url}") String successUrl,
            @Value("${stripe.cancel-url}") String cancelUrl,
            BalanceService balanceService
    ) {
        this.secretKey = secretKey;
        this.webhookSuccessSecretKey = webhookSuccessSecretKey;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
        this.balanceService = balanceService;
    }

    @Override
    public StripeResponseDto createPayment(BigDecimal amount, Long balanceId) {
        checkBalanceExist(balanceId);
        Stripe.apiKey = secretKey;
        SessionCreateParams params = buildSessionCreateParams(amount, balanceId);
        Session session = tryCreateSession(params);
        return StripeResponseDto.builder()
                .status(SUCCESS_STATUS)
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    private void checkBalanceExist(Long balanceId) {
        if (!balanceService.isBalanceExist(balanceId)) {
            throw new RuntimeException("Balance not found");//TODO custom exception
        }
    }

    private Session tryCreateSession(SessionCreateParams params) {
        try {
            return Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);//TODO custom exception
        }
    }

    private SessionCreateParams buildSessionCreateParams(BigDecimal amount, Long balanceId) {
        SessionCreateParams.LineItem.PriceData.ProductData productData = getProductData();
        SessionCreateParams.LineItem.PriceData priceData = getPriceData(amount, productData);
        SessionCreateParams.LineItem lineItem = getLineItem(priceData);
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(lineItem)
                .putMetadata(BALANCE_ID, balanceId.toString())
                .build();
    }

    private SessionCreateParams.LineItem getLineItem(SessionCreateParams.LineItem.PriceData priceData) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(QUANTITY)
                .setPriceData(priceData)
                .build();
    }

    private SessionCreateParams.LineItem.PriceData getPriceData(
            BigDecimal amount,
            SessionCreateParams.LineItem.PriceData.ProductData productData
    ) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(CURRENCY)
                .setUnitAmount(sumUnitAmount(amount))
                .setProductData(productData)
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData getProductData() {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(BALANCE_TOP_UP)
                .build();
    }

    private long sumUnitAmount(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(COINS)).longValue();
    }

    @Override
    public CapturePaymentResponseDto capturePayment(String sessionId) {
        Stripe.apiKey = secretKey;
        Session session = tryRetrieveSession(sessionId);
        String paymentStatus = session.getPaymentStatus();
        return CapturePaymentResponseDto
                .builder()
                .sessionId(sessionId)
                .sessionStatus(paymentStatus)
                .paymentStatus(session.getPaymentStatus())
                .build();
    }

    private Session tryRetrieveSession(String sessionId) {
        try {
            return Session.retrieve(sessionId);
        } catch (StripeException e) {
            throw new RuntimeException(e);//TODO custom exception
        }
    }

    @Override
    public void handleSuccessWebhook(String payload, String sigHeader) {
        Event event = tryGetEvent(payload, sigHeader);
        if (CHECKOUT_SESSION_COMPLETED.equals(event.getType())) {
            Long balanceId = extractBalanceId(payload);
            BigDecimal amount = extractAmountTotal(payload);
            BigDecimal finalAmount = amount.divide(BigDecimal.valueOf(COINS), RoundingMode.DOWN);
            balanceService.refillBalance(finalAmount, balanceId);
        }
    }

    private Event tryGetEvent(String payload, String sigHeader) {
        try {
            return Webhook.constructEvent(
                    payload, sigHeader, webhookSuccessSecretKey
            );
        } catch (SignatureVerificationException ex) {
            throw new RuntimeException(ex);//TODO custom exception
        }
    }

    private Long extractBalanceId(String payload) {
        JsonNode rootNode = tryGetJsonNode(payload);
        JsonNode metadataNode = rootNode.path("data").path("object").path("metadata");
        return metadataNode.path(BALANCE_ID).asLong();
    }

    private BigDecimal extractAmountTotal(String payload) {
        JsonNode rootNode = tryGetJsonNode(payload);
        JsonNode amountTotalNode = rootNode.path("data").path("object").path("amount_total");
        return BigDecimal.valueOf(amountTotalNode.asLong());
    }

    private JsonNode tryGetJsonNode(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);//TODO custom exception
        }
    }

}