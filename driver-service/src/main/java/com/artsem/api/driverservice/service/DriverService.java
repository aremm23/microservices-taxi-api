package com.artsem.api.driverservice.service;


import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {

    Page<DriverResponseDto> getList(DriverFilter filter, Pageable pageable);

    DriverResponseDto getOne(Long id);

    ListResponseDto<DriverResponseDto> getMany(List<Long> ids);

    DriverResponseDto create(DriverRequestDto Driver);

    DriverResponseDto patch(Long id, DriverUpdateRequestDto DriverDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

    DriverResponseDto updateDriverStatus(Long id, DriverStatusUpdateRequestDto statusUpdateDto);

    DriverAndCarResponseDto assignCarToDriver(Long driverId, Long carId);

    DriverAndCarResponseDto removeCarFromDriver(Long driverId);

    DriverAndCarResponseDto getOneWithCar(Long id);
}
