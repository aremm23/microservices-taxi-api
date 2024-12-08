package com.artsem.api.paymentservice.kafka.consumer;

import com.artsem.api.paymentservice.kafka.PaymentProcessMessage;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;
import com.artsem.api.paymentservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentProcessConsumer {

    public static final String PAYMENT_PROCESS_GROUP = "payment-process-group";
    public static final String PAYMENT_KAFKA_LISTENER_CONTAINER_FACTORY = "paymentKafkaListenerContainerFactory";
    private static final String PAYMENT_PROCESS_TOPIC = "payment-process-topic";

    private final BalanceService balanceService;

    @KafkaListener(
            topics = PAYMENT_PROCESS_TOPIC,
            groupId = PAYMENT_PROCESS_GROUP,
            containerFactory = PAYMENT_KAFKA_LISTENER_CONTAINER_FACTORY
    )
    public void consume(PaymentProcessMessage paymentProcessMessage) {
        log.info("Json message received -> {}", paymentProcessMessage);
        BalanceResponseDto balanceResponseDto = balanceService.getByUserId(paymentProcessMessage.userId());
        balanceService.charge(balanceResponseDto.getId(), paymentProcessMessage.amount());
    }

}