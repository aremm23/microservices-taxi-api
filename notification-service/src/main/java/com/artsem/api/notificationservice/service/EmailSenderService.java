package com.artsem.api.notificationservice.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {
    @Async
    void trySendEmail(String to, String subject, String htmlBody);
}
