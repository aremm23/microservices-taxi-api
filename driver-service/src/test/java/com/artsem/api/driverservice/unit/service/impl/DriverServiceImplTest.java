package com.artsem.api.driverservice.unit.service.impl;

import com.artsem.api.driverservice.exceptions.EmptyIdsListException;
import com.artsem.api.driverservice.exceptions.car.CarNotFoundException;
import com.artsem.api.driverservice.exceptions.driver.DriverNotCreatedException;
import com.artsem.api.driverservice.exceptions.driver.DriverNotFoundException;
import com.artsem.api.driverservice.exceptions.driver.DriverNotUpdatedException;
import com.artsem.api.driverservice.exceptions.driver.DriversNotFoundException;
import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.response.CarResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.response.ListResponseDto;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.repository.DriverRepository;
import com.artsem.api.driverservice.service.impl.DriverServiceImpl;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import com.artsem.api.driverservice.util.DriverServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void getList_shouldHandlePaginationCorrectly() {
        DriverFilter filter = new DriverFilter(DriverServiceImplTestUtil.SURNAME_LIKE, null);
        Pageable pageable = PageRequest.of(0, 10);
        List<Driver> driverList = DriverServiceImplTestUtil.getDriverList();
        Page<Driver> driverPage = new PageImpl<>(driverList, pageable, driverList.size());
        List<DriverResponseDto> expectedDtoList = DriverServiceImplTestUtil.getDriverResponseDtoList().list();

        when(driverRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(driverPage);
        when(mapper.map(any(Driver.class), eq(DriverResponseDto.class)))
                .thenAnswer(invocation -> {
                    Driver driver = invocation.getArgument(0);
                    return expectedDtoList.stream()
                            .filter(dto -> dto.getId().equals(driver.getId()))
                            .findFirst()
                            .orElse(null);
                });

        Page<DriverResponseDto> result = driverService.getList(filter, pageable);

        assertEquals(driverPage.getTotalElements(), result.getTotalElements());
        assertEquals(driverPage.getNumber(), result.getNumber());
        assertEquals(driverPage.getSize(), result.getSize());
        assertEquals(expectedDtoList, result.getContent());
        verify(driverRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(driverList.size())).map(any(Driver.class), eq(DriverResponseDto.class));
    }


    @Test
    void getOne_existingDriver_returnsDriverResponseDto() {
        Long existingDriverId = DriverServiceImplTestUtil.FIRST_ID;
        Driver existingDriver = DriverServiceImplTestUtil.getFirstDriver();
        DriverResponseDto expectedResponse = DriverServiceImplTestUtil.getFirstDriverResponseDto();

        when(driverRepository.findById(existingDriverId)).thenReturn(Optional.of(existingDriver));
        when(mapper.map(existingDriver, DriverResponseDto.class)).thenReturn(expectedResponse);

        DriverResponseDto actualResponse = driverService.getOne(existingDriverId);

        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void getOne_nonExistingDriver_throwsDriverNotFoundException() {
        when(driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.getOne(DriverServiceImplTestUtil.FIRST_ID));
    }

    @Test
    void create_validDriver_returnsDriverResponseDto() {
        DriverRequestDto requestDto = DriverServiceImplTestUtil.getFirstDriverRequestDto();
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();
        DriverResponseDto responseDto = DriverServiceImplTestUtil.getFirstDriverResponseDto();

        when(driverRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(mapper.map(requestDto, Driver.class)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(mapper.map(driver, DriverResponseDto.class)).thenReturn(responseDto);

        DriverResponseDto result = driverService.create(requestDto);

        assertEquals(responseDto, result);
    }

    @Test
    void create_existingEmail_throwsDriverNotCreatedException() {
        DriverRequestDto requestDto = DriverServiceImplTestUtil.getFirstDriverRequestDto();

        when(driverRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);

        assertThrows(DriverNotCreatedException.class, () -> driverService.create(requestDto));
    }

    @Test
    void patch_existingDriver_updatesFieldsAndReturnsUpdatedDriverResponseDto() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();
        DriverUpdateRequestDto updateRequestDto = DriverServiceImplTestUtil.getUpdateDriverRequestDto();
        Driver updatedDriver = DriverServiceImplTestUtil.getFirstDriver();
        updatedDriver.setEmail(DriverServiceImplTestUtil.UPDATED_EMAIL);
        DriverResponseDto updatedResponseDto = DriverServiceImplTestUtil.getUpdatedDriverResponseDto();

        when(driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(driver));
        when(driverRepository.findIdByEmail(updateRequestDto.getEmail())).thenReturn(null);
        when(driverRepository.save(driver)).thenReturn(updatedDriver);
        when(mapper.map(updatedDriver, DriverResponseDto.class)).thenReturn(updatedResponseDto);

        DriverResponseDto result = driverService.patch(DriverServiceImplTestUtil.FIRST_ID, updateRequestDto);

        assertEquals(updatedResponseDto, result);
    }

    @Test
    void patch_existingEmail_throwsDriverNotUpdatedException() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();
        DriverUpdateRequestDto updateRequestDto = DriverServiceImplTestUtil.getUpdateDriverRequestDto();

        when(driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(driver));
        when(driverRepository.findIdByEmail(updateRequestDto.getEmail())).thenReturn(DriverServiceImplTestUtil.SECOND_ID);

        assertThrows(DriverNotUpdatedException.class, () -> driverService.patch(DriverServiceImplTestUtil.FIRST_ID, updateRequestDto));
    }

    @Test
    void delete_existingDriver_deletesDriver() {
        when(driverRepository.existsById(DriverServiceImplTestUtil.FIRST_ID)).thenReturn(true);

        driverService.delete(DriverServiceImplTestUtil.FIRST_ID);

        verify(driverRepository).deleteById(DriverServiceImplTestUtil.FIRST_ID);
    }

    @Test
    void delete_nonExistingDriver_throwsDriverNotFoundException() {
        when(driverRepository.existsById(DriverServiceImplTestUtil.FIRST_ID)).thenReturn(false);

        assertThrows(DriverNotFoundException.class, () -> driverService.delete(DriverServiceImplTestUtil.FIRST_ID));
    }

    @Test
    void create_withExistingEmailDifferentCase_throwsDriverNotCreatedException() {
        DriverRequestDto requestDto = DriverServiceImplTestUtil.getFirstDriverRequestDto();
        String existingEmail = requestDto.getEmail();

        when(driverRepository.existsByEmail(existingEmail)).thenReturn(true);

        assertThrows(DriverNotCreatedException.class, () -> driverService.create(requestDto));

        verify(driverRepository).existsByEmail(existingEmail);
        verify(driverRepository, never()).save(any(Driver.class));
    }

    @Test
    void deleteMany_whenNotAllIdsExist_throwsDriversNotFoundException() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Driver> existingDrivers = List.of(
                DriverServiceImplTestUtil.getFirstDriver(),
                DriverServiceImplTestUtil.getSecondDriver()
        );

        when(driverRepository.findAllById(ids)).thenReturn(existingDrivers);

        assertThrows(DriversNotFoundException.class, () -> driverService.deleteMany(ids));

        verify(driverRepository).findAllById(ids);
        verify(driverRepository, never()).deleteAll(any());
    }

    @Test
    void deleteMany_withEmptyIdsList_throwsEmptyIdsListException() {
        List<Long> emptyList = List.of();

        assertThrows(EmptyIdsListException.class, () -> driverService.deleteMany(emptyList));
    }

    @Test
    void getMany_withExistingIds_returnsListOfDriverResponseDto() {
        List<Long> ids = List.of(DriverServiceImplTestUtil.FIRST_ID, DriverServiceImplTestUtil.SECOND_ID);
        List<Driver> drivers = DriverServiceImplTestUtil.getDriverList();
        ListResponseDto<DriverResponseDto> expectedResponseDtos = DriverServiceImplTestUtil.getDriverResponseDtoList();
        List<DriverResponseDto> driverResponseDtoList = expectedResponseDtos.list();

        when(driverRepository.findAllById(ids)).thenReturn(drivers);
        when(mapper.map(drivers.get(0), DriverResponseDto.class)).thenReturn(driverResponseDtoList.get(0));
        when(mapper.map(drivers.get(1), DriverResponseDto.class)).thenReturn(driverResponseDtoList.get(1));

        ListResponseDto<DriverResponseDto> result = driverService.getMany(ids);

        assertEquals(expectedResponseDtos, result);
    }

    @Test
    void getMany_withNonExistingIds_returnsEmptyList() {
        List<Long> ids = List.of(DriverServiceImplTestUtil.NON_EXISTING_ID, DriverServiceImplTestUtil.NON_EXISTING_ID_2);

        when(driverRepository.findAllById(ids)).thenReturn(List.of());

        ListResponseDto<DriverResponseDto> result = driverService.getMany(ids);

        assertTrue(result.list().isEmpty());
    }

    @Test
    void assignCarToDriver_existingDriverAndExistingCar_returnsDriverAndCarResponseDto() {
        Driver existingDriver = DriverServiceImplTestUtil.getFirstDriver();
        Car carToAssign = CarServiceImplTestUtil.getFirstCar();
        Driver driverWithCar = DriverServiceImplTestUtil.getFirstDriver();
        driverWithCar.setCar(carToAssign);

        CarResponseDto carResponseDto = CarServiceImplTestUtil.getFirstCarResponseDto();
        DriverAndCarResponseDto expectedResponseDto = DriverAndCarResponseDto.builder()
                .id(existingDriver.getId())
                .email(existingDriver.getEmail())
                .surname(existingDriver.getSurname())
                .firstname(existingDriver.getFirstname())
                .car(carResponseDto)
                .build();

        when(driverRepository.findById(existingDriver.getId())).thenReturn(Optional.of(existingDriver));
        when(carRepository.findById(carToAssign.getId())).thenReturn(Optional.of(carToAssign));
        when(driverRepository.save(existingDriver)).thenReturn(driverWithCar);
        when(mapper.map(driverWithCar, DriverAndCarResponseDto.class)).thenReturn(expectedResponseDto);

        DriverAndCarResponseDto actualResponse = driverService.assignCarToDriver(existingDriver.getId(), carToAssign.getId());

        assertNotNull(actualResponse);
        assertEquals(expectedResponseDto.getId(), actualResponse.getId());
        assertEquals(expectedResponseDto.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponseDto.getCar().getId(), actualResponse.getCar().getId());
        assertEquals(expectedResponseDto.getCar().getLicensePlate(), actualResponse.getCar().getLicensePlate());

        verify(driverRepository).findById(existingDriver.getId());
        verify(carRepository).findById(carToAssign.getId());
        verify(driverRepository).save(existingDriver);
        verify(mapper).map(driverWithCar, DriverAndCarResponseDto.class);
    }

    @Test
    void removeCarFromDriver_existingDriverWithCar_removesCarAndReturnsDto() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();
        Car car = CarServiceImplTestUtil.getFirstCar();
        driver.setCar(car);
        Driver updatedDriver = DriverServiceImplTestUtil.getFirstDriver();
        updatedDriver.setCar(null);
        DriverAndCarResponseDto expectedResponse = DriverAndCarResponseDto.builder()
                .id(driver.getId())
                .email(driver.getEmail())
                .surname(driver.getSurname())
                .firstname(driver.getFirstname())
                .car(null)
                .build();

        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(updatedDriver);
        when(mapper.map(updatedDriver, DriverAndCarResponseDto.class)).thenReturn(expectedResponse);

        DriverAndCarResponseDto response = driverService.removeCarFromDriver(driver.getId());

        assertEquals(expectedResponse, response);
        assertNull(driver.getCar());
        verify(driverRepository).save(driver);
        verify(mapper).map(updatedDriver, DriverAndCarResponseDto.class);
    }

    @Test
    void removeCarFromDriver_driverWithoutCar_throwsCarNotFoundException() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();

        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));

        assertThrows(CarNotFoundException.class, () -> driverService.removeCarFromDriver(driver.getId()));

        verify(driverRepository, never()).save(any(Driver.class));
    }


    @Test
    void updateDriverStatus_existingDriver_updatesStatusAndReturnsDto() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriver();
        Driver updatedDriver = DriverServiceImplTestUtil.getFirstDriver();
        updatedDriver.setIsFree(true);
        DriverStatusUpdateRequestDto statusUpdateDto = new DriverStatusUpdateRequestDto(true);
        DriverResponseDto expectedResponse =  DriverServiceImplTestUtil.getFirstDriverResponseDto();

        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(updatedDriver);
        when(mapper.map(updatedDriver, DriverResponseDto.class)).thenReturn(expectedResponse);

        DriverResponseDto response = driverService.updateDriverStatus(driver.getId(), statusUpdateDto);

        assertEquals(expectedResponse, response);
        assertTrue(driver.getIsFree());
        verify(driverRepository).save(driver);
        verify(mapper).map(updatedDriver, DriverResponseDto.class);
    }

    @Test
    void updateDriverStatus_nonExistingDriver_throwsDriverNotFoundException() {
        long driverId = 1L;
        DriverStatusUpdateRequestDto statusUpdateDto = new DriverStatusUpdateRequestDto(true);

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class, () -> driverService.updateDriverStatus(driverId, statusUpdateDto));

        verify(driverRepository, never()).save(any(Driver.class));
    }

}