package com.artsem.api.passengerservice.service;

import com.artsem.api.passengerservice.broker.UserCreateMessage;

public interface AuthInteractionService {

    void createPassenger(UserCreateMessage userCreateMessage);

}
