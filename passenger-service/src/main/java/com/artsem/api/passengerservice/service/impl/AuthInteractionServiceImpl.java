package com.artsem.api.passengerservice.service.impl;

import com.artsem.api.passengerservice.broker.UserIdMessage;
import com.artsem.api.passengerservice.broker.UserRollbackMessage;
import com.artsem.api.passengerservice.broker.UserCreateMessage;
import com.artsem.api.passengerservice.broker.producer.PassengerIdProducer;
import com.artsem.api.passengerservice.broker.producer.PassengerNotCreatedRollbackProducer;
import com.artsem.api.passengerservice.exceptions.PassengerNotCreatedException;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.service.AuthInteractionService;
import com.artsem.api.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthInteractionServiceImpl implements AuthInteractionService {

    private final PassengerIdProducer passengerIdProducer;

    private final PassengerNotCreatedRollbackProducer passengerNotCreatedRollbackProducer;

    private final PassengerService passengerService;

    @Override
    public void createPassenger(UserCreateMessage userCreateMessage) {
        try {
            tryCreatePassenger(userCreateMessage);
        } catch (PassengerNotCreatedException e) {
            createRollback(userCreateMessage.keycloakId());
        }
    }

    private void createRollback(String keycloakId) {
        UserRollbackMessage userRollbackMessage = UserRollbackMessage.builder()
                .keycloakId(keycloakId)
                .build();
        passengerNotCreatedRollbackProducer.sendRollbackEvent(userRollbackMessage);
    }

    private void tryCreatePassenger(UserCreateMessage userCreateMessage) {
        PassengerRequestDto passengerRequestDto = parseUserCreateMessageToPassengerRequestDto(userCreateMessage);
        PassengerResponseDto savedPassengerDto = passengerService.create(passengerRequestDto);
        UserIdMessage userIdMessage = createPassengerIdMessage(
                userCreateMessage.keycloakId(),
                savedPassengerDto.getId()
        );
        passengerIdProducer.publishPassengerIdEvent(userIdMessage);
    }

    private UserIdMessage createPassengerIdMessage(String keycloakId, Long id) {
        return UserIdMessage.builder()
                .userId(id)
                .userKeycloakId(keycloakId)
                .build();
    }

    private PassengerRequestDto parseUserCreateMessageToPassengerRequestDto(UserCreateMessage userCreateMessage) {
        return PassengerRequestDto.builder()
                .email(userCreateMessage.email())
                .firstname(userCreateMessage.firstname())
                .surname(userCreateMessage.lastname())
                .build();
    }

}
