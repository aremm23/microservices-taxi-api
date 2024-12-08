package com.artsem.api.paymentservice.config;

import com.artsem.api.paymentservice.exceptions.UnableParseMessageException;
import com.artsem.api.paymentservice.kafka.PaymentProcessMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Custom Kafka deserializer for PaymentProcessMessage.
 * This deserializer uses Jackson's ObjectMapper to convert JSON payloads into Java objects.
 */
@Slf4j
@NoArgsConstructor
public class CustomPaymentProcessMessageDeserializer implements Deserializer<PaymentProcessMessage> {

    private ObjectMapper objectMapper;

    /**
     * Default constructor initializes the ObjectMapper.
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public PaymentProcessMessage deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(data, PaymentProcessMessage.class);
        } catch (Exception e) {
            log.error("Error deserializing message: {}", e.getMessage(), e);
            throw new UnableParseMessageException(e.getMessage());
        }
    }

    @Override
    public void close() {
        // No resources to close, but method implementation is required.
    }
}