package com.artsem.api.passengerservice.service;

import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;

import java.util.List;

public interface PassengerService {

    PassengerResponseDto getOne(Long id);

    List<PassengerResponseDto> getMany(List<Long> ids);

    PassengerResponseDto create(PassengerRequestDto passenger);

    PassengerResponseDto patch(Long id, PassengerUpdateRequestDto passengerDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
