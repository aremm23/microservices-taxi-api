package com.artsem.api.authservice.broker.producer;

import com.artsem.api.authservice.config.RabbitConfig;
import com.artsem.api.authservice.config.RabbitQueueConfig;
import com.artsem.api.authservice.model.UserCreateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedProducer {

    private final AmqpTemplate amqpTemplate;

    public void publishUserCreatedEvent(String role, UserCreateMessage message) {
        String routingKeyTemplate = "user.%s.created";
        String routingKey = routingKeyTemplate.formatted(role);
        try {
            amqpTemplate.convertAndSend(RabbitQueueConfig.USER_EXCHANGE, routingKey, message);
            log.info("Successfully sent {} created message: {}", role, message);
        } catch (AmqpException ex) {
            log.error("Failed to send {} created message: {}", role, message, ex);
        }
    }

}