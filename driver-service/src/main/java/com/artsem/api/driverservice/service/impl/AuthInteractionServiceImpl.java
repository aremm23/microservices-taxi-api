package com.artsem.api.driverservice.service.impl;

import com.artsem.api.driverservice.broker.UserCreateMessage;
import com.artsem.api.driverservice.broker.UserIdMessage;
import com.artsem.api.driverservice.broker.UserRollbackMessage;
import com.artsem.api.driverservice.broker.producer.DriverIdProducer;
import com.artsem.api.driverservice.broker.producer.DriverNotCreatedRollbackProducer;
import com.artsem.api.driverservice.exceptions.driver.DriverNotCreatedException;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.response.DriverResponseDto;
import com.artsem.api.driverservice.service.AuthInteractionService;
import com.artsem.api.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthInteractionServiceImpl implements AuthInteractionService {

    private final DriverIdProducer driverIdProducer;

    private final DriverNotCreatedRollbackProducer driverNotCreatedRollbackProducer;

    private final DriverService driverService;

    @Override
    public void createDriver(UserCreateMessage userCreateMessage) {
        try {
            tryCreateDriver(userCreateMessage);
        } catch (DriverNotCreatedException e) {
            createRollback(userCreateMessage.keycloakId());
        }
    }

    private void createRollback(String keycloakId) {
        UserRollbackMessage userRollbackMessage = UserRollbackMessage.builder()
                .keycloakId(keycloakId)
                .build();
        driverNotCreatedRollbackProducer.sendRollbackEvent(userRollbackMessage);
    }

    private void tryCreateDriver(UserCreateMessage userCreateMessage) {
        DriverRequestDto driverRequestDto = parseUserCreateMessageToDriverRequestDto(userCreateMessage);
        DriverResponseDto savedDriverDto = driverService.create(driverRequestDto);
        UserIdMessage userIdMessage = createDriverIdMessage(
                userCreateMessage.keycloakId(),
                savedDriverDto.getId()
        );
        driverIdProducer.publishDriverIdEvent(userIdMessage);
    }

    private UserIdMessage createDriverIdMessage(String keycloakId, Long id) {
        return UserIdMessage.builder()
                .userId(id)
                .userKeycloakId(keycloakId)
                .build();
    }

    private DriverRequestDto parseUserCreateMessageToDriverRequestDto(UserCreateMessage userCreateMessage) {
        return DriverRequestDto.builder()
                .email(userCreateMessage.email())
                .firstname(userCreateMessage.firstname())
                .surname(userCreateMessage.lastname())
                .build();
    }

}
