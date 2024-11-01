package com.artsem.api.driverservice.service;


import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto getOne(Long id);

    List<CarResponseDto> getMany(List<Long> ids);

    CarResponseDto create(CarRequestDto Car);

    CarResponseDto patch(Long id, CarUpdateRequestDto CarDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
