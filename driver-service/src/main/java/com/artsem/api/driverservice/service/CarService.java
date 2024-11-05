package com.artsem.api.driverservice.service;


import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {

    Page<CarResponseDto> getList(CarFilter filter, Pageable pageable);

    CarResponseDto getOne(Long id);

    ListResponseDto<CarResponseDto> getMany(List<Long> ids);

    CarResponseDto create(CarRequestDto Car);

    CarResponseDto patch(Long id, CarUpdateRequestDto CarDto);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
