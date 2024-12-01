package com.artsem.api.driverservice.broker.producer;

import com.artsem.api.driverservice.broker.UserIdMessage;
import com.artsem.api.driverservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DriverIdProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishDriverIdEvent(UserIdMessage userIdMessage) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.CALLBACK_EXCHANGE,
                RabbitConfig.USER_CALLBACK_KEY,
                userIdMessage
        );
        log.info("DriverId event sent: {}", userIdMessage);
    }
}
