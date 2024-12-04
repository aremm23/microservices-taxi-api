package com.artsem.api.notificationservice.service;

public interface EmailSenderService {
    void trySendEmail(String to, String subject, String htmlBody);
}
