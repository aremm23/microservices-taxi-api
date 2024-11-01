package com.artsem.api.driverservice.service;


import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;

import java.util.List;

public interface DriverService {

    DriverResponseDto getOne(Long id);

    List<DriverResponseDto> getMany(List<Long> ids);

    DriverResponseDto create(DriverRequestDto Driver);

    DriverResponseDto patch(Long id, DriverUpdateRequestDto DriverDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

    DriverResponseDto updateDriverStatus(Long id, DriverStatusUpdateRequestDto statusUpdateDto);

    DriverAndCarResponseDto assignCarToDriver(Long driverId, Long carId);

    DriverAndCarResponseDto removeCarFromDriver(Long driverId);

    DriverAndCarResponseDto getOneWithCar(Long id);
}
