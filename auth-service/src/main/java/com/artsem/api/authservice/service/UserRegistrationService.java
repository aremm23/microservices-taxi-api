package com.artsem.api.authservice.service;

import com.artsem.api.authservice.model.UserRegisterDto;

public interface UserRegistrationService {

    void createAdmin(UserRegisterDto userRegisterDto);

    void createPassenger(UserRegisterDto userRegisterDto);

    void createDriver(UserRegisterDto userRegisterDto);

}
