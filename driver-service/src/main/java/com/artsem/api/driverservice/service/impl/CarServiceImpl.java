package com.artsem.api.driverservice.service.impl;

import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Page<CarResponseDto> getList(CarFilter filter, Pageable pageable) {
        Specification<Car> spec = filter.toSpecification();
        Page<Car> cars = carRepository.findAll(spec, pageable);
        return cars.map(car -> mapper.map(car, CarResponseDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public CarResponseDto getOne(Long id) {
        Car car = findCarById(id);
        return mapper.map(car, CarResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ListResponseDto<CarResponseDto> getMany(List<Long> ids) {
        List<Car> cars = carRepository.findAllById(ids);
        List<CarResponseDto> dtosList = cars.stream()
                .map(car -> mapper.map(car, CarResponseDto.class))
                .toList();
        return new ListResponseDto<>(dtosList.size(), dtosList);
    }

    @Transactional
    @Override
    public CarResponseDto create(CarRequestDto carDto) {
        Car car = mapper.map(carDto, Car.class);
        checkIsLicensePlateExist(carDto);
        Car savedCar = carRepository.save(car);
        return mapper.map(savedCar, CarResponseDto.class);
    }

    @Transactional
    @Override
    public CarResponseDto patch(Long id, CarUpdateRequestDto carDto) {
        Car car = findCarById(id);
        checkExistingLicensePlate(id, carDto.getLicensePlate());
        updateFields(carDto, car);
        Car updatedCar = carRepository.save(car);
        return mapper.map(updatedCar, CarResponseDto.class);
    }

    private void checkExistingLicensePlate(Long id, String newLicensePlate) {
        Long existingCarWithLicensePlateId = carRepository.findIdByLicensePlate(newLicensePlate);
        if (existingCarWithLicensePlateId != null && !existingCarWithLicensePlateId.equals(id)) {
            throw new RuntimeException("Such license plate number already exists");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car with id %d not found".formatted(id));
        }
        carRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteMany(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("ID list cannot be empty");
        }
        List<Car> cars = carRepository.findAllById(ids);
        if (cars.size() != ids.size()) {
            throw new RuntimeException("Some cars not found for the provided IDs");
        }
        carRepository.deleteAll(cars);
    }

    private void checkIsLicensePlateExist(CarRequestDto carDto) {
        if (carRepository.existsByLicensePlate(carDto.getLicensePlate())) {
            throw new RuntimeException("Car with such license plate number already exist.");
        }
    }

    private Car findCarById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Car with id %d not found".formatted(id))
        );
    }

    private void updateFields(CarUpdateRequestDto CarDto, Car car) {
        Optional.ofNullable(CarDto.getModel())
                .ifPresent(car::setModel);
        Optional.ofNullable(CarDto.getCarCategory())
                .ifPresent(car::setCarCategory);
        Optional.ofNullable(CarDto.getLicensePlate())
                .ifPresent(car::setLicensePlate);
    }

}