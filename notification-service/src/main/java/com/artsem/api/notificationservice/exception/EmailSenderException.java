package com.artsem.api.notificationservice.exception;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(Throwable cause) {
        super("Failed to send email: %s".formatted(cause));
    }
}
