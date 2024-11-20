package com.artsem.api.authservice.service.kafka.producer.impl;

import com.artsem.api.authservice.service.kafka.producer.NotificationProducer;
import org.springframework.stereotype.Service;

/**
 * Currently, this class is a placeholder and needs to be implemented.
 * Once implemented, it will handle sending various notification types,
 * such as email confirmations and alerts.
 */
@Service
public class MockNotificationProducer implements NotificationProducer {
    // TODO: Implement message production logic for notifications
    @Override
    public void sendEmailVerificationMessage(String email, String token) {

    }
}
