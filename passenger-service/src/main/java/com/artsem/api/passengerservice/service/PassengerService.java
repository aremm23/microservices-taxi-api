package com.artsem.api.passengerservice.service;

import com.artsem.api.passengerservice.model.dto.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.PassengerResponseDto;
import com.artsem.api.passengerservice.model.dto.PassengerUpdateRequestDto;

import java.util.List;

public interface PassengerService {

    PassengerResponseDto getOne(Long id);

    List<PassengerResponseDto> getMany(List<Long> ids);

    PassengerResponseDto create(PassengerRequestDto passenger);

    PassengerResponseDto patch(Long id, PassengerUpdateRequestDto passengerDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
