package com.artsem.api.passengerservice.config;

import com.artsem.api.passengerservice.broker.UserCreateMessage;
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

    public static final String USER_CALLBACK_KEY = "user.callback";
    public static final String USER_ROLLBACK_KEY = "user.rollback";
    public static final String PASSENGER_CREATED_QUEUE = "passenger-created-queue";
    public static final String CALLBACK_EXCHANGE = "auth.callback.exchange";
    public static final String ROLLBACK_EXCHANGE = "auth.rollback.exchange";
    public static final Map<String, Class<?>> MESSAGE_MAPPINGS = Map.of(
            "com.artsem.api.authservice.model.UserCreateMessage", UserCreateMessage.class
    );
    private static final String[] TRUSTED_PACKAGES = {
            "com.artsem.api.authservice.broker"
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
