package com.artsem.api.notificationservice.service.impl;

import com.artsem.api.notificationservice.dto.UserEmailResponseDto;
import com.artsem.api.notificationservice.exception.IncorrectResponseBodyException;
import com.artsem.api.notificationservice.feign.client.DriverServiceClient;
import com.artsem.api.notificationservice.feign.client.PassengerServiceClient;
import com.artsem.api.notificationservice.service.UserEmailDefinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEmailDefinerServiceImpl implements UserEmailDefinerService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final DriverServiceClient driverServiceClient;

    private final PassengerServiceClient passengerServiceClient;

    @Cacheable(value = "passengerEmails", key = "#passengerId")
    @Override
    public String definePassengerEmail(String passengerId) {
        UserEmailResponseDto passengerEmailResponseDto = passengerServiceClient.getPassengerEmailById(passengerId);
        checkIsResponseValid(passengerId, passengerEmailResponseDto);
        return passengerEmailResponseDto.email();
    }

    @Cacheable(value = "driverEmails", key = "#driverId")
    @Override
    public String defineDriverEmail(String driverId) {
        UserEmailResponseDto driverEmailResponseDto = driverServiceClient.getDriverEmailById(driverId);
        checkIsResponseValid(driverId, driverEmailResponseDto);
        return driverEmailResponseDto.email();
    }

    private void checkIsResponseValid(String driverId, UserEmailResponseDto userEmailResponseDto) {
        if(!userEmailResponseDto.id().toString().equals(driverId) || userEmailResponseDto.email().isEmpty()) {
            throw new IncorrectResponseBodyException();
        }
    }

    @Override
    public List<String> defineRecentDriversEmails() {
        Set<String> keys = redisTemplate.keys("driverEmails::*");
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }
        return keys.stream()
                .map(key -> (String) redisTemplate.opsForValue().get(key))
                .filter(Objects::nonNull)
                .toList();
    }
}
