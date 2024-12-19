package com.artsem.api.driverservice.broker.consumer;

import com.artsem.api.driverservice.broker.UserCreateMessage;
import com.artsem.api.driverservice.config.RabbitConfig;
import com.artsem.api.driverservice.service.AuthInteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DriverCreatedConsumer {

    private final AuthInteractionService authInteractionService;

    @RabbitListener(queues = "${rabbitmq.queues.driverCreated}")
    public void receiveDriverCreatedMessage(UserCreateMessage userCreateMessage) {
        log.info("Received driver created message: {}", userCreateMessage);
        authInteractionService.createDriver(userCreateMessage);
    }
}
