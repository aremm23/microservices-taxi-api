package com.artsem.api.passengerservice.broker.producer;

import com.artsem.api.passengerservice.broker.UserIdMessage;
import com.artsem.api.passengerservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassengerIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishPassengerIdEvent(UserIdMessage userIdMessage) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.CALLBACK_EXCHANGE,
                RabbitConfig.USER_CALLBACK_KEY,
                userIdMessage
        );
        log.info("PassengerId event sent: {}", userIdMessage);
    }
}