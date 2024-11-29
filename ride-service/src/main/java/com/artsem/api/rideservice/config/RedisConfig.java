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
                .withCacheConfiguration("distanceAndDurationCache",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(30))
                                .serializeValuesWith(getValueSerializationPair())
                                .serializeKeysWith(getKeySerializationPair()))
                .withCacheConfiguration("passengersIdCache",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(3))
                                .serializeValuesWith(getValueSerializationPair())
                                .serializeKeysWith(getKeySerializationPair()))
                .build();
    }
}