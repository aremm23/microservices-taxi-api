package com.artsem.api.authservice.broker.consumer;

import com.artsem.api.authservice.broker.UserRollbackMessage;
import com.artsem.api.authservice.config.RabbitQueueConfig;
import com.artsem.api.authservice.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRollbackConsumer {

    private final KeycloakService keycloakService;

    @RabbitListener(queues = RabbitQueueConfig.ROLLBACK_QUEUE)
    public void handleUserRollbackMessage(UserRollbackMessage message) {
        String userKeycloakId = message.keycloakId();
        log.info("Received user rollback message for user with id {}", userKeycloakId);
        keycloakService.deleteUser(userKeycloakId);
    }
}
