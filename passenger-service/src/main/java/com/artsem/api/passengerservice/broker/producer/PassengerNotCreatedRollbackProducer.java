package com.artsem.api.passengerservice.broker.producer;

import com.artsem.api.passengerservice.broker.UserRollbackMessage;
import com.artsem.api.passengerservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerNotCreatedRollbackProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendRollbackEvent(UserRollbackMessage userRollbackMessage) {
        rabbitTemplate.convertAndSend(RabbitConfig.ROLLBACK_EXCHANGE, RabbitConfig.USER_ROLLBACK_KEY, userRollbackMessage);
        log.info("Rollback event sent: {}", userRollbackMessage);
    }
}
