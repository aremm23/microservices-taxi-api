package com.artsem.api.notificationservice.service.impl;

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

    @Cacheable(value = "passengerEmails", key = "#passengerId")
    @Override
    public String definePassengerEmail(String passengerId) {
        //TODO synchronous request to passenger-service
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "am2134877@gmail.com";
    }

    @Cacheable(value = "driverEmails", key = "#driverId")
    @Override
    public String defineDriverEmail(String driverId) {
        //TODO synchronous request to driver-service
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "am2134877@gmail.com";
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
