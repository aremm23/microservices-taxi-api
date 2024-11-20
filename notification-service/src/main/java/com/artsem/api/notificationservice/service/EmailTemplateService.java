package com.artsem.api.notificationservice.service;

import com.artsem.api.notificationservice.dto.AcceptedRideDto;
import com.artsem.api.notificationservice.dto.EmailConfirmationDto;
import com.artsem.api.notificationservice.dto.FinishedRideDto;
import com.artsem.api.notificationservice.dto.RequestRideDto;

public interface EmailTemplateService {
    void emailConfirmation(EmailConfirmationDto emailConfirmationDto);

    void requestRide(RequestRideDto requestRideDto);

    void acceptRide(AcceptedRideDto acceptedRideDto);

    void finishRide(FinishedRideDto finishedRideDto);
}
