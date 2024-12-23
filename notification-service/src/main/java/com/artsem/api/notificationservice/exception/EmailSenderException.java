package com.artsem.api.notificationservice.exception;

import com.artsem.api.notificationservice.util.ExceptionKeys;
import jakarta.mail.MessagingException;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(MessagingException cause) {
        super(ExceptionKeys.EMAIL_SENDER_EXCEPTION.formatted(cause));
    }
}
