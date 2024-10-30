package com.artsem.api.authservice.service.kafka.producer;

import org.springframework.stereotype.Service;


/**
 * NotificationProducer is responsible for producing notification messages
 * to a Kafka topic.
 */
@Service
public interface NotificationProducer {
    void send();
}