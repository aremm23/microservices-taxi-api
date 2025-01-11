package com.artsem.api.authservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    public static final String PASSENGER_CREATED_KEY = "user.passenger.created";
    public static final String DRIVER_CREATED_KEY = "user.driver.created";
    public static final String USER_EXCHANGE = "user-exchange";
    public static final String USER_CALLBACK_KEY = "user.callback";
    public static final String PASSENGER_CREATED_QUEUE = "passenger-created-queue";
    public static final String DRIVER_CREATED_QUEUE = "driver-created-queue";
    public static final String CALLBACK_EXCHANGE = "auth.callback.exchange";
    public static final String CALLBACK_QUEUE = "auth.callback.queue";
    public static final String ROLLBACK_EXCHANGE = "auth.rollback.exchange";
    public static final String ROLLBACK_QUEUE = "auth.rollback.queue";
    public static final String USER_ROLLBACK_KEY = "user.rollback";
    public static final String NOTIFICATION_EXCHANGE = "notification-exchange";
    public static final String NOTIFICATION_QUEUE = "notification-email-verification-queue";
    public static final String NOTIFICATION_ROUTING_KEY = "auth.email.notification";

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding notificationBinding(
            @Qualifier("notificationQueue") Queue notificationQueue,
            @Qualifier("notificationExchange") TopicExchange notificationExchange
    ) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public DirectExchange authRollbackExchange() {
        return new DirectExchange(ROLLBACK_EXCHANGE);
    }

    @Bean
    public Queue authRollbackQueue() {
        return new Queue(ROLLBACK_QUEUE, true);
    }

    @Bean
    public Binding authRollbackBinding(
            @Qualifier("authRollbackQueue") Queue authRollbackQueue,
            @Qualifier("authRollbackExchange") DirectExchange authRollbackExchange
    ) {
        return BindingBuilder.bind(authRollbackQueue).to(authRollbackExchange).with(USER_ROLLBACK_KEY);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue passengerQueue() {
        return new Queue(PASSENGER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue driverQueue() {
        return new Queue(DRIVER_CREATED_QUEUE, true);
    }

    @Bean
    public DirectExchange authCallbackExchange() {
        return new DirectExchange(CALLBACK_EXCHANGE);
    }

    @Bean
    public Queue authCallbackQueue() {
        return new Queue(CALLBACK_QUEUE, true);
    }

    @Bean
    public Binding authCallbackBinding(
            @Qualifier("authCallbackQueue") Queue authCallbackQueue,
            @Qualifier("authCallbackExchange") DirectExchange authCallbackExchange
    ) {
        return BindingBuilder.bind(authCallbackQueue).to(authCallbackExchange).with(USER_CALLBACK_KEY);
    }

    @Bean
    public Binding passengerBinding(
            @Qualifier("passengerQueue") Queue passengerQueue,
            @Qualifier("userExchange") TopicExchange userExchange
    ) {
        return BindingBuilder.bind(passengerQueue).to(userExchange).with(PASSENGER_CREATED_KEY);
    }

    @Bean
    public Binding driverBinding(
            @Qualifier("driverQueue") Queue driverQueue,
            @Qualifier("userExchange") TopicExchange userExchange
    ) {
        return BindingBuilder.bind(driverQueue).to(userExchange).with(DRIVER_CREATED_KEY);
    }
}
