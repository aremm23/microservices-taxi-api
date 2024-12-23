package com.artsem.api.authservice.broker.producer;

import com.artsem.api.authservice.broker.EmailVerificationMessage;
import com.artsem.api.authservice.config.RabbitQueueConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailVerifyNotificationProducer {

    private final AmqpTemplate amqpTemplate;

    public void publishEmailVerifyMessage(EmailVerificationMessage emailVerificationMessage) {
        try {
            amqpTemplate.convertAndSend(
                    RabbitQueueConfig.NOTIFICATION_EXCHANGE,
                    RabbitQueueConfig.NOTIFICATION_ROUTING_KEY,
                    emailVerificationMessage
            );
            log.info("Successfully sent email verification message: {}", emailVerificationMessage);
        } catch (AmqpException ex) {
            log.error("Failed to send email verification message: {}", emailVerificationMessage, ex);
        }
    }

}