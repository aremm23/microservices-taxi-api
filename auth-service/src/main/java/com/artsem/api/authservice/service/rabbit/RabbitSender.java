package com.artsem.api.authservice.service.rabbit;

import com.artsem.api.authservice.model.UserRegisterDto;
import org.springframework.stereotype.Service;


/**
 * RabbitSender is responsible for sending messages related to user registration
 * via RabbitMQ. This class will handle sending messages for different types of users.
 */
@Service
public interface RabbitSender {

    /**
     * Sends a message specifically for passenger registration.
     *
     * @param userRegisterDto The record containing passenger registration details.
     */
    void sendPassenger(UserRegisterDto userRegisterDto);


    /**
     * Sends a message specifically for driver registration.
     *
     * @param userRegisterDto The record containing driver registration details.
     */
    void sendDriver(UserRegisterDto userRegisterDto);
}
