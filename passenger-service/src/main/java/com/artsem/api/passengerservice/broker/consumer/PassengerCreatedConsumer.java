package com.artsem.api.passengerservice.broker.consumer;

import com.artsem.api.passengerservice.broker.UserCreateMessage;
import com.artsem.api.passengerservice.config.RabbitConfig;
import com.artsem.api.passengerservice.service.AuthInteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PassengerCreatedConsumer {

    private final AuthInteractionService authInteractionService;

    @RabbitListener(queues = RabbitConfig.PASSENGER_CREATED_QUEUE)
    public void receivePassengerCreatedMessage(UserCreateMessage userCreateMessage) {
        log.info("Received passenger created message: {}", userCreateMessage);
        authInteractionService.createPassenger(userCreateMessage);
    }
}