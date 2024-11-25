package com.artsem.api.driverservice.unit.service.impl;

import com.artsem.api.driverservice.exceptions.EmptyIdsListException;
import com.artsem.api.driverservice.exceptions.car.CarNotFoundException;
import com.artsem.api.driverservice.exceptions.car.CarsNotFoundException;
import com.artsem.api.driverservice.exceptions.car.ExistingLicensePlateException;
import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.service.impl.CarServiceImpl;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void getList_shouldHandlePaginationCorrectly() {
        CarFilter filter = new CarFilter(null, null);
        Pageable pageable = PageRequest.of(0, 10);
        List<Car> carList = CarServiceImplTestUtil.getCarList();
        Page<Car> carPage = new PageImpl<>(carList, pageable, carList.size());
        List<CarResponseDto> expectedDtoList = CarServiceImplTestUtil.getCarResponseDtoList().list();

        when(carRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(carPage);
        when(mapper.map(any(Car.class), eq(CarResponseDto.class)))
                .thenAnswer(invocation -> {
                    Car car = invocation.getArgument(0);
                    return expectedDtoList.stream()
                            .filter(dto -> dto.getId().equals(car.getId()))
                            .findFirst()
                            .orElse(null);
                });

        Page<CarResponseDto> result = carService.getList(filter, pageable);

        assertEquals(carPage.getTotalElements(), result.getTotalElements());
        assertEquals(carPage.getNumber(), result.getNumber());
        assertEquals(carPage.getSize(), result.getSize());
        assertEquals(expectedDtoList, result.getContent());

        verify(carRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(carList.size())).map(any(Car.class), eq(CarResponseDto.class));
    }

    @Test
    void getOne_existingCar_returnsCarResponseDto() {
        Car firstCar = CarServiceImplTestUtil.getFirstCar();
        CarResponseDto firstCarDto = CarServiceImplTestUtil.getFirstCarResponseDto();

        when(carRepository.findById(CarServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(firstCar));
        when(mapper.map(firstCar, CarResponseDto.class)).thenReturn(firstCarDto);

        CarResponseDto result = carService.getOne(CarServiceImplTestUtil.FIRST_ID);

        assertEquals(firstCarDto, result);
        verify(carRepository, times(1)).findById(CarServiceImplTestUtil.FIRST_ID);
    }

    @Test
    void getOne_nonExistingCar_throwsCarNotFoundException() {
        when(carRepository.findById(CarServiceImplTestUtil.NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.getOne(CarServiceImplTestUtil.NON_EXISTING_ID));
        verify(carRepository, times(1)).findById(CarServiceImplTestUtil.NON_EXISTING_ID);
    }

    @Test
    void create_validRequest_savesCarAndReturnsResponseDto() {
        CarRequestDto requestDto = CarServiceImplTestUtil.getFirstCarRequestDto();
        Car savedCar = CarServiceImplTestUtil.getFirstCar();
        CarResponseDto responseDto = CarServiceImplTestUtil.getFirstCarResponseDto();

        when(mapper.map(requestDto, Car.class)).thenReturn(savedCar);
        when(carRepository.existsByLicensePlate(requestDto.getLicensePlate())).thenReturn(false);
        when(carRepository.save(savedCar)).thenReturn(savedCar);
        when(mapper.map(savedCar, CarResponseDto.class)).thenReturn(responseDto);

        CarResponseDto result = carService.create(requestDto);

        assertEquals(responseDto, result);
        verify(carRepository, times(1)).existsByLicensePlate(requestDto.getLicensePlate());
        verify(carRepository, times(1)).save(savedCar);
    }

    @Test
    void create_duplicateLicensePlate_throwsExistingLicensePlateException() {
        CarRequestDto requestDto = CarServiceImplTestUtil.getFirstCarRequestDto();

        when(carRepository.existsByLicensePlate(requestDto.getLicensePlate())).thenReturn(true);

        assertThrows(ExistingLicensePlateException.class, () -> carService.create(requestDto));
        verify(carRepository, times(1)).existsByLicensePlate(requestDto.getLicensePlate());
    }

    @Test
    void patch_existingCar_updatesCarAndReturnsResponseDto() {
        CarUpdateRequestDto updateDto = CarServiceImplTestUtil.getUpdateCarRequestDto();
        Car car = CarServiceImplTestUtil.getFirstCar();
        CarResponseDto updatedCarDto = CarServiceImplTestUtil.getUpdatedCarResponseDto();
        Car updatedCar = CarServiceImplTestUtil.getFirstCar();
        updatedCar.setModel(CarServiceImplTestUtil.UPDATED_MODEL);

        when(carRepository.findById(CarServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(car));
        when(carRepository.findIdByLicensePlate(updateDto.getLicensePlate())).thenReturn(null);
        when(carRepository.save(car)).thenReturn(updatedCar);
        when(mapper.map(updatedCar, CarResponseDto.class)).thenReturn(updatedCarDto);

        CarResponseDto result = carService.patch(CarServiceImplTestUtil.FIRST_ID, updateDto);

        assertEquals(updatedCarDto, result);
        verify(carRepository, times(1)).findById(CarServiceImplTestUtil.FIRST_ID);
        verify(carRepository, times(1)).findIdByLicensePlate(updateDto.getLicensePlate());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void getMany_existingCars_returnsListResponseDto() {
        List<Long> carIds = List.of(1L, 2L);
        List<Car> cars = CarServiceImplTestUtil.getCarList();
        List<CarResponseDto> expectedDtoList = CarServiceImplTestUtil.getCarResponseDtoList().list();

        // Настройка репозитория и маппера
        when(carRepository.findAllById(carIds)).thenReturn(cars);
        when(mapper.map(any(Car.class), eq(CarResponseDto.class)))
                .thenAnswer(invocation -> {
                    Car car = invocation.getArgument(0);
                    return expectedDtoList.stream()
                            .filter(dto -> dto.getId().equals(car.getId()))
                            .findFirst()
                            .orElse(null);
                });

        // Вызов метода getMany
        ListResponseDto<CarResponseDto> result = carService.getMany(carIds);

        // Проверка результатов
        assertEquals(expectedDtoList.size(), result.size());
        assertEquals(expectedDtoList, result.list());
        verify(carRepository, times(1)).findAllById(carIds);
    }

    @Test
    void patch_nonExistingCar_throwsCarNotFoundException() {
        CarUpdateRequestDto updateDto = CarServiceImplTestUtil.getUpdateCarRequestDto();

        when(carRepository.findById(CarServiceImplTestUtil.NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.patch(CarServiceImplTestUtil.NON_EXISTING_ID, updateDto));
        verify(carRepository, times(1)).findById(CarServiceImplTestUtil.NON_EXISTING_ID);
    }

    @Test
    void delete_existingCar_deletesCar() {
        when(carRepository.existsById(CarServiceImplTestUtil.FIRST_ID)).thenReturn(true);

        carService.delete(CarServiceImplTestUtil.FIRST_ID);

        verify(carRepository, times(1)).existsById(CarServiceImplTestUtil.FIRST_ID);
        verify(carRepository, times(1)).deleteById(CarServiceImplTestUtil.FIRST_ID);
    }

    @Test
    void delete_nonExistingCar_throwsCarNotFoundException() {
        when(carRepository.existsById(CarServiceImplTestUtil.NON_EXISTING_ID)).thenReturn(false);

        assertThrows(CarNotFoundException.class, () -> carService.delete(CarServiceImplTestUtil.NON_EXISTING_ID));
        verify(carRepository, times(1)).existsById(CarServiceImplTestUtil.NON_EXISTING_ID);
    }

    @Test
    void deleteMany_existingCars_deletesCars() {
        List<Long> carIds = List.of(1L, 2L);
        List<Car> cars = CarServiceImplTestUtil.getCarList();

        when(carRepository.findAllById(carIds)).thenReturn(cars);

        carService.deleteMany(carIds);

        verify(carRepository, times(1)).findAllById(carIds);
        verify(carRepository, times(1)).deleteAll(cars);
    }

    @Test
    void deleteMany_nonExistingCars_throwsCarsNotFoundException() {
        List<Long> carIds = List.of(1L, 2L);

        when(carRepository.findAllById(carIds)).thenReturn(List.of());

        assertThrows(CarsNotFoundException.class, () -> carService.deleteMany(carIds));
        verify(carRepository, times(1)).findAllById(carIds);
    }

    @Test
    void deleteMany_emptyIdsList_throwsEmptyIdsListException() {
        List<Long> carIds = List.of();

        assertThrows(EmptyIdsListException.class, () -> carService.deleteMany(carIds));
    }
}

