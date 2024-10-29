package com.artsem.api.authservice.service.rabbit;

import com.artsem.api.authservice.model.UserRegisterDto;
import org.springframework.stereotype.Service;


/**
 * RabbitSenderImpl is responsible for sending messages related to user registration
 * via RabbitMQ. This class will handle sending messages for different types of users.
 *
 * Currently, this class is a placeholder and needs to be implemented.
 */
@Service
public class RabbitSenderImpl {

    /**
     * Sends a message specifically for passenger registration.
     *
     * @param userRegisterDto The record containing passenger registration details.
     */
    public void sendPassenger(UserRegisterDto userRegisterDto) {
        // TODO: Implement the logic to send a passenger registration message
    }

    /**
     * Sends a message specifically for driver registration.
     *
     * @param userRegisterDto The record containing driver registration details.
     */
    public void sendDriver(UserRegisterDto userRegisterDto) {
        // TODO: Implement the logic to send a driver registration message
    }
}
