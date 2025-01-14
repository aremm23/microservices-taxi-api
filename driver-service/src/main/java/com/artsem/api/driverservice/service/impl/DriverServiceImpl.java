package com.artsem.api.driverservice.service.impl;

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
import com.artsem.api.driverservice.model.dto.response.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverEmailResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.response.DriverStatusResponseDto;
import com.artsem.api.driverservice.model.dto.response.ListResponseDto;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.repository.DriverRepository;
import com.artsem.api.driverservice.service.DriverService;
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
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Page<DriverResponseDto> getList(DriverFilter filter, Pageable pageable) {
        Specification<Driver> spec = filter.toSpecification();
        Page<Driver> drivers = driverRepository.findAll(spec, pageable);
        return drivers.map(driver -> mapper.map(driver, DriverResponseDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public DriverResponseDto getOne(Long id) {
        Driver driver = findDriverById(id);
        return mapper.map(driver, DriverResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ListResponseDto<DriverResponseDto> getMany(List<Long> ids) {
        List<Driver> drivers = driverRepository.findAllById(ids);
        List<DriverResponseDto> dtosList = drivers.stream()
                .map(driver -> mapper.map(driver, DriverResponseDto.class))
                .toList();
        return ListResponseDto.<DriverResponseDto>builder()
                .size(dtosList.size())
                .list(dtosList)
                .build();
    }

    @Override
    public DriverEmailResponseDto getEmailById(Long id) {
        String email = driverRepository.findEmailById(id).orElseThrow(
                DriverNotFoundException::new
        );
        return DriverEmailResponseDto.builder()
                .email(email)
                .id(id)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DriverStatusResponseDto getDriverStatus(Long id) {
        Driver driver = findDriverById(id);
        return DriverStatusResponseDto.builder()
                .isFree(driver.getIsFree())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public DriverAndCarResponseDto getOneWithCar(Long id) {
        Driver driver = findDriverById(id);
        return mapper.map(driver, DriverAndCarResponseDto.class);
    }

    @Transactional
    @Override
    public DriverResponseDto create(DriverRequestDto driverDto) {
        Driver driver = mapper.map(driverDto, Driver.class);
        checkIsEmailExist(driverDto);
        Driver savedDriver = driverRepository.save(driver);
        return mapper.map(savedDriver, DriverResponseDto.class);
    }

    @Transactional
    @Override
    public DriverResponseDto patch(Long id, DriverUpdateRequestDto driverDto) {
        Driver driver = findDriverById(id);
        checkExistingEmail(id, driverDto.getEmail());
        updateFields(driverDto, driver);
        Driver updatedDriver = driverRepository.save(driver);
        return mapper.map(updatedDriver, DriverResponseDto.class);
    }

    private void checkExistingEmail(Long id, String newEmail) {
        Long existingDriverWithEmailId = driverRepository.findIdByEmail(newEmail);

        if (existingDriverWithEmailId != null && !existingDriverWithEmailId.equals(id)) {
            throw new DriverNotUpdatedException();
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new DriverNotFoundException();
        }
        driverRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteMany(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new EmptyIdsListException();
        }
        List<Driver> drivers = driverRepository.findAllById(ids);
        if (drivers.size() != ids.size()) {
            throw new DriversNotFoundException();
        }
        driverRepository.deleteAll(drivers);
    }

    @Transactional
    @Override
    public DriverResponseDto updateDriverStatus(Long id, DriverStatusUpdateRequestDto statusUpdateDto) {
        Driver driver = findDriverById(id);
        driver.setIsFree(statusUpdateDto.isFree());
        Driver updatedDriver = driverRepository.save(driver);
        return mapper.map(updatedDriver, DriverResponseDto.class);
    }

    @Transactional
    @Override
    public DriverAndCarResponseDto assignCarToDriver(Long driverId, Long carId) {
        Driver driver = findDriverById(driverId);
        if (driver.getCar() != null) {
            throw new DriverNotUpdatedException();
        }
        Car car = carRepository.findById(carId)
                .orElseThrow(CarNotFoundException::new);
        driver.setCar(car);
        Driver updatedDriver = driverRepository.save(driver);
        return mapper.map(updatedDriver, DriverAndCarResponseDto.class);
    }

    @Transactional
    @Override
    public DriverAndCarResponseDto removeCarFromDriver(Long driverId) {
        Driver driver = findDriverById(driverId);
        if (driver.getCar() == null) {
            throw new CarNotFoundException();
        }
        driver.setCar(null);
        Driver updatedDriver = driverRepository.save(driver);
        return mapper.map(updatedDriver, DriverAndCarResponseDto.class);
    }

    private void checkIsEmailExist(DriverRequestDto driverDto) {
        if (driverRepository.existsByEmail(driverDto.getEmail())) {
            throw new DriverNotCreatedException();
        }
    }

    private Driver findDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow(
                DriverNotFoundException::new
        );
    }

    private void updateFields(DriverUpdateRequestDto DriverDto, Driver driver) {
        Optional.ofNullable(DriverDto.getEmail())
                .ifPresent(driver::setEmail);
        Optional.ofNullable(DriverDto.getFirstname())
                .ifPresent(driver::setFirstname);
        Optional.ofNullable(DriverDto.getSurname())
                .ifPresent(driver::setSurname);
    }

}
