package com.artsem.api.rideservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class KafkaTopicConfig {

    public static final String PAYMENT_PROCESS_TOPIC = "payment-process-topic";

    private final int topicPartitions;
    private final int topicReplicas;

    public KafkaTopicConfig(
            @Value("${kafka.topic.payment-process-topic.partitions}")
            int topicPartitions,
            @Value("${kafka.topic.payment-process-topic.replicas}")
            int topicReplicas) {
        this.topicPartitions = topicPartitions;
        this.topicReplicas = topicReplicas;
    }

    @Bean
    public NewTopic createPaymentProcessTopic() {
        log.info(
                "Creating topic: {} with partitions: {} and replicas: {}",
                PAYMENT_PROCESS_TOPIC,
                topicPartitions,
                topicReplicas
        );
        return TopicBuilder.name(PAYMENT_PROCESS_TOPIC)
                .partitions(topicPartitions)
                .replicas(topicReplicas)
                .build();
    }

}