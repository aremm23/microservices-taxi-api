package com.artsem.api.driverservice.broker.producer;

import com.artsem.api.driverservice.broker.UserRollbackMessage;
import com.artsem.api.driverservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DriverNotCreatedRollbackProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String rollbackExchange;
    private final String userRollbackKey;

    public DriverNotCreatedRollbackProducer(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.exchanges.rollback}") String rollbackExchange,
            @Value("${rabbitmq.keys.userRollback}") String userRollbackKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.rollbackExchange = rollbackExchange;
        this.userRollbackKey = userRollbackKey;
    }

    public void sendRollbackEvent(UserRollbackMessage userRollbackMessage) {
        rabbitTemplate.convertAndSend(
                rollbackExchange,
                userRollbackKey,
                userRollbackMessage
        );
        log.info("Rollback event sent: {}", userRollbackMessage);
    }
}