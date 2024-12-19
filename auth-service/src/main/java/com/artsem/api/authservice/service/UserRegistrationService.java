package com.artsem.api.authservice.service;

import com.artsem.api.authservice.model.AdminRegisterRequestDto;
import com.artsem.api.authservice.model.UserRegisterRequestDto;

public interface UserRegistrationService {

    void createAdmin(AdminRegisterRequestDto adminRegisterRequestDto);

    void createUser(UserRegisterRequestDto userRegisterRequestDto);

}
