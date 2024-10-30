package com.artsem.api.authservice.service;

import com.artsem.api.authservice.model.UserRegisterDto;

public interface UserRegistrationService {

    void createAdmin(UserRegisterDto userRegisterDto);

    void createUser(UserRegisterDto userRegisterDto);

}
