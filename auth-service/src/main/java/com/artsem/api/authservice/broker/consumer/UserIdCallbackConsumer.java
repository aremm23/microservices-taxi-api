package com.artsem.api.authservice.broker.consumer;

import com.artsem.api.authservice.broker.UserIdConsumerMessage;
import com.artsem.api.authservice.config.RabbitQueueConfig;
import com.artsem.api.authservice.model.UserIdsMessage;
import com.artsem.api.authservice.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserIdCallbackConsumer {

    private final KeycloakService keycloakService;

    @RabbitListener(queues = RabbitQueueConfig.CALLBACK_QUEUE)
    public void receivePassengerCreatedMessage(UserIdConsumerMessage userIdConsumerMessage) {
        log.info("Received user id message: {}", userIdConsumerMessage);
        keycloakService.setUserSequenceId(parseUserIdMessage(userIdConsumerMessage));
    }

    private UserIdsMessage parseUserIdMessage(UserIdConsumerMessage userIdConsumerMessage) {
        return UserIdsMessage.builder()
                .userKeycloakId(userIdConsumerMessage.userKeycloakId())
                .userSequenceId(userIdConsumerMessage.userId().toString())
                .build();
    }
}
