package com.artsem.api.rideservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    public static final String DISTANCE_AND_DURATION_CACHE_NAME = "distanceAndDurationCache";
    public static final String PASSENGERS_ID_CACHE_NAME = "passengersIdCache";
    public static final int DISTANCE_AND_DURATION_CACHE_TTL_SECONDS = 30;
    public static final int PASSENGER_ID_CACHE_TTL_HOURS = 3;

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
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .withCacheConfiguration(DISTANCE_AND_DURATION_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(DISTANCE_AND_DURATION_CACHE_TTL_SECONDS))
                                .serializeValuesWith(getValueSerializationPair())
                                .serializeKeysWith(getKeySerializationPair()))
                .withCacheConfiguration(PASSENGERS_ID_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(PASSENGER_ID_CACHE_TTL_HOURS))
                                .serializeValuesWith(getValueSerializationPair())
                                .serializeKeysWith(getKeySerializationPair()))
                .build();
    }
}