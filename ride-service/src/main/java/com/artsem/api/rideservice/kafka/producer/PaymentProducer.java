package com.artsem.api.rideservice.kafka.producer;

import com.artsem.api.rideservice.kafka.PaymentProcessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private static final String PAYMENT_TOPIC = "payment-process-topic";

    private final KafkaTemplate<String, PaymentProcessMessage> kafkaTemplate;

    public void sendMessage(PaymentProcessMessage paymentProcessMessage) {
        CompletableFuture<SendResult<String, PaymentProcessMessage>> future =
                kafkaTemplate.send(PAYMENT_TOPIC, paymentProcessMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                handleSuccess(result, paymentProcessMessage);
            } else {
                handleFailure(ex, paymentProcessMessage);
            }
        });
    }

    private void handleSuccess(SendResult<String, PaymentProcessMessage> result, PaymentProcessMessage message) {
        log.info("Sent message=['{}'] with offset=['{}']", message, result.getRecordMetadata().offset());
    }

    private void handleFailure(Throwable ex, PaymentProcessMessage message) {
        log.error("Unable to send message=['{}']", message, ex);
    }

}
