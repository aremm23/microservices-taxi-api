package com.artsem.api.authservice.service.rabbit.impl;

import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.service.rabbit.RabbitSender;
import org.springframework.stereotype.Service;

/**
* This class is a placeholder and needs to be implemented.
 */
@Service
public class MockRabbitSender implements RabbitSender {
    // TODO: Implement the logic to send a passenger registration message
    @Override
    public void sendPassenger(UserRegisterDto userRegisterDto) {

    }

    // TODO: Implement the logic to send a driver registration message
    @Override
    public void sendDriver(UserRegisterDto userRegisterDto) {

    }
}
