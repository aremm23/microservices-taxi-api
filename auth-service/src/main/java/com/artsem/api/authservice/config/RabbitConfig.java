package com.artsem.api.authservice.config;

import com.artsem.api.authservice.broker.UserIdConsumerMessage;
import com.artsem.api.authservice.broker.UserRollbackMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final Map<String, Class<?>> MESSAGE_MAPPINGS = Map.of(
            "com.artsem.api.passengerservice.broker.UserIdMessage", UserIdConsumerMessage.class,
            "com.artsem.api.driverservice.broker.UserIdMessage", UserIdConsumerMessage.class,
            "com.artsem.api.driverservice.broker.UserRollbackMessage", UserRollbackMessage.class,
            "com.artsem.api.passengerservice.broker.UserRollbackMessage", UserRollbackMessage.class
    );
    private static final String[] TRUSTED_PACKAGES = {
            "com.artsem.api.passengerservice.broker",
            "com.artsem.api.driverservice.broker"
    };

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public RabbitConfig(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages(TRUSTED_PACKAGES);
        classMapper.setIdClassMapping(MESSAGE_MAPPINGS);
        return classMapper;
    }

}
