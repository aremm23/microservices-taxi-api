package com.artsem.api.passengerservice.service.impl;

import com.artsem.api.passengerservice.exceptions.PassengerNotCreatedException;
import com.artsem.api.passengerservice.exceptions.PassengerNotFoundedException;
import com.artsem.api.passengerservice.model.Passenger;
import com.artsem.api.passengerservice.model.dto.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.PassengerResponseDto;
import com.artsem.api.passengerservice.repository.PassengerRepository;
import com.artsem.api.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private final ModelMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public PassengerResponseDto getOne(Long id) {
        Passenger passenger = findPassengerById(id);
        return mapper.map(passenger, PassengerResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PassengerResponseDto> getMany(List<Long> ids) {
        List<Passenger> passengers = passengerRepository.findAllById(ids);
        if (passengers.isEmpty()) {
            throw new PassengerNotFoundedException("No passengers found for the provided IDs");
        }
        return passengers.stream()
                .map(passenger -> mapper.map(passenger, PassengerResponseDto.class))
                .toList();
    }

    @Transactional
    @Override
    public PassengerResponseDto create(PassengerRequestDto passengerDto) {
        Passenger passenger = mapper.map(passengerDto, Passenger.class);
        checkIsPhoneOrEmailExist(passengerDto);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return mapper.map(savedPassenger, PassengerResponseDto.class);
    }

    @Transactional
    @Override
    public PassengerResponseDto patch(Long id, PassengerRequestDto passengerDto) {
        Passenger passenger = findPassengerById(id);
        checkIsPhoneOrEmailExist(passengerDto);
        updateFields(passengerDto, passenger);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return mapper.map(updatedPassenger, PassengerResponseDto.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new PassengerNotFoundedException("Passenger with id %d not found".formatted(id));
        }
        passengerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteMany(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("ID list cannot be empty");
        }
        List<Passenger> passengers = passengerRepository.findAllById(ids);
        if (passengers.size() != ids.size()) {
            throw new PassengerNotFoundedException("Some passengers not found for the provided IDs");
        }
        passengerRepository.deleteAll(passengers);
    }

    private void checkIsPhoneOrEmailExist(PassengerRequestDto passengerDto) {
        if (passengerRepository.existsByEmailOrPhone(passengerDto.getEmail(), passengerDto.getPhone())) {
            throw new PassengerNotCreatedException("Passenger with such phone or email already exist.");
        }
    }

    private Passenger findPassengerById(Long id) {
        return passengerRepository.findById(id).orElseThrow(
                () -> new PassengerNotFoundedException("Passenger with id %d not found".formatted(id))
        );
    }

    private void updateFields(PassengerRequestDto passengerDto, Passenger passenger) {
        Optional.ofNullable(passengerDto.getEmail()).ifPresent(passenger::setEmail);
        Optional.ofNullable(passengerDto.getPhone()).ifPresent(passenger::setPhone);
        Optional.ofNullable(passengerDto.getFirstname()).ifPresent(passenger::setFirstname);
        Optional.ofNullable(passengerDto.getSurname()).ifPresent(passenger::setSurname);
    }

}
