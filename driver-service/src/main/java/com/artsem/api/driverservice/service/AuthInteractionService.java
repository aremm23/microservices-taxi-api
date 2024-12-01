package com.artsem.api.driverservice.service;

import com.artsem.api.driverservice.broker.UserCreateMessage;

public interface AuthInteractionService {

    void createDriver(UserCreateMessage userCreateMessage);

}
