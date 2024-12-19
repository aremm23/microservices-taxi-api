package com.artsem.api.driverservice.broker.producer;

import com.artsem.api.driverservice.broker.UserIdMessage;
import com.artsem.api.driverservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DriverIdProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String callbackExchange;
    private final String userCallbackKey;

    public DriverIdProducer(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchanges.callback}") String callbackExchange,
            @Value("${rabbitmq.keys.userCallback}") String userCallbackKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.callbackExchange = callbackExchange;
        this.userCallbackKey = userCallbackKey;
    }

    public void publishDriverIdEvent(UserIdMessage userIdMessage) {
        rabbitTemplate.convertAndSend(
                callbackExchange,
                userCallbackKey,
                userIdMessage
        );
        log.info("DriverId event sent: {}", userIdMessage);
    }
}