package com.artsem.api.authservice.service.rabbit;

import com.artsem.api.authservice.model.UserCreateMessage;


/**
 * RabbitSender is responsible for sending messages related to user registration
 * via RabbitMQ. This class will handle sending messages for different types of users.
 */
public interface RabbitSender {

    /**
     * Sends a message specifically for passenger registration.
     *
     * @param userCreateMessage The record containing passenger registration details.
     */
    void sendPassenger(UserCreateMessage userCreateMessage);


    /**
     * Sends a message specifically for driver registration.
     *
     * @param userCreateMessage The record containing driver registration details.
     */
    void sendDriver(UserCreateMessage userCreateMessage);
}
