package com.artsem.api.notificationservice.broker.consumer;

import com.artsem.api.notificationservice.config.RabbitConfig;
import com.artsem.api.notificationservice.dto.EmailConfirmationDto;
import com.artsem.api.notificationservice.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConfirmationMessageConsumer {

    private final EmailTemplateService emailTemplateService;

    @RabbitListener(queues = RabbitConfig.NOTIFICATION_QUEUE)
    public void receivePassengerCreatedMessage(EmailConfirmationDto emailConfirmationDto) {
        log.info("Received email verification message: {}", emailConfirmationDto);
        emailTemplateService.emailConfirmationTemplate(emailConfirmationDto);
    }

}
