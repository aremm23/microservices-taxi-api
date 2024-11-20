package com.artsem.api.notificationservice.service.impl;

import com.artsem.api.notificationservice.exception.EmailSenderException;
import com.artsem.api.notificationservice.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailAddress;

    @Async
    @Override
    public void trySendEmail(String to, String subject, String htmlBody) {
        try {
            send(to, subject, htmlBody);
        } catch (MessagingException e) {
            throw new EmailSenderException(e);
        }
    }

    private void send(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = createMimeMessage(to, subject, htmlBody);
        emailSender.send(message);
    }

    private MimeMessage createMimeMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailAddress);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        return message;
    }
}
