package com.artsem.api.passengerservice.service;

import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerEmailResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PassengerService {

    Page<PassengerResponseDto> getList(PassengerFilter filter, Pageable pageable);

    PassengerResponseDto getOne(Long id);

    PassengerEmailResponseDto getEmail(Long id);

    ListResponseDto<PassengerResponseDto> getMany(List<Long> ids);

    PassengerResponseDto create(PassengerRequestDto passenger);

    PassengerResponseDto patch(Long id, PassengerUpdateRequestDto passengerDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
