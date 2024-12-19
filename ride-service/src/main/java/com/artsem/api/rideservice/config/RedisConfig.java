package com.artsem.api.rideservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    public static final String DISTANCE_AND_DURATION_CACHE_NAME = "distanceAndDurationCache";
    public static final String PASSENGERS_ID_CACHE_NAME = "passengersIdCache";
    public static final String DRIVERS_ID_CACHE_NAME = "driversIdCache";

    private final int distanceAndDurationCacheTtlSeconds;
    private final int passengerIdCacheTtlHours;
    private final int driverIdCacheTtlHours;

    public RedisConfig(
            @Value("${cache.distance-and-duration.ttl-seconds}") int distanceAndDurationCacheTtlSeconds,
            @Value("${cache.passenger-id.ttl-hours}") int passengerIdCacheTtlHours,
            @Value("${cache.driver-id.ttl-hours}") int driverIdCacheTtlHours
    ) {
        this.distanceAndDurationCacheTtlSeconds = distanceAndDurationCacheTtlSeconds;
        this.passengerIdCacheTtlHours = passengerIdCacheTtlHours;
        this.driverIdCacheTtlHours = driverIdCacheTtlHours;
    }

    private static RedisSerializationContext.SerializationPair<Object> getValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()
        );
    }

    private static RedisSerializationContext.SerializationPair<String> getKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(
                new StringRedisSerializer()
        );
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Long> longSerializer = new Jackson2JsonRedisSerializer<>(Long.class);

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .withCacheConfiguration(DISTANCE_AND_DURATION_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(distanceAndDurationCacheTtlSeconds))
                                .serializeValuesWith(getValueSerializationPair())
                                .serializeKeysWith(getKeySerializationPair()))
                .withCacheConfiguration(PASSENGERS_ID_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(passengerIdCacheTtlHours))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(longSerializer))
                                .serializeKeysWith(getKeySerializationPair()))
                .withCacheConfiguration(DRIVERS_ID_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(driverIdCacheTtlHours))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(longSerializer))
                                .serializeKeysWith(getKeySerializationPair()))
                .build();
    }
}