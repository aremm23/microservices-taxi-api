package com.artsem.api.authservice.service.kafka.producer;

/**
 * NotificationProducer is responsible for producing notification messages
 * to a Kafka topic.
 */
public interface NotificationProducer {
    void send();
}