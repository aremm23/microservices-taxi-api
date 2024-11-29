package com.artsem.api.notificationservice.service;

import com.artsem.api.notificationservice.dto.AcceptedRideDto;
import com.artsem.api.notificationservice.dto.EmailConfirmationDto;
import com.artsem.api.notificationservice.dto.FinishedRideDto;
import com.artsem.api.notificationservice.dto.RequestRideDto;

public interface EmailTemplateService {
    void emailConfirmationTemplate(EmailConfirmationDto emailConfirmationDto);

    void requestRideTemplate(RequestRideDto requestRideDto);

    void acceptRideTemplate(AcceptedRideDto acceptedRideDto);

    void finishRideTemplate(FinishedRideDto finishedRideDto);
}
