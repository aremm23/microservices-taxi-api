package com.artsem.api.authservice.config;

import com.artsem.api.authservice.model.AdminRegisterRequestDto;
import com.artsem.api.authservice.model.UserRegisterDto;
import com.artsem.api.authservice.model.UserRegisterRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RegisterMapper {

    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    UserRegisterDto toUserRegisterDto(UserRegisterRequestDto userRegisterRequestDto);

    UserRegisterDto toUserRegisterDto(AdminRegisterRequestDto adminRegisterRequestDto);

}
