package com.artsem.api.notificationservice.service.impl;

import com.artsem.api.notificationservice.dto.AcceptedRideDto;
import com.artsem.api.notificationservice.dto.EmailConfirmationDto;
import com.artsem.api.notificationservice.dto.FinishedRideDto;
import com.artsem.api.notificationservice.dto.RequestRideDto;
import com.artsem.api.notificationservice.service.EmailSenderService;
import com.artsem.api.notificationservice.service.EmailTemplateService;
import com.artsem.api.notificationservice.service.UserEmailDefinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    public static final String REQUESTED_RIDE_MESSAGE_TEMPLATE =
            """
                    pick up: %s;
                    drop off: %s;
                    price: %s;
                    tariff: %s;
                    payment method: %s;
                    distance: %s;
                    rideId: %s;
                    passengerId: %s;
                    """;
    public static final String RIDE_REQUESTED_SUBJECT = "Ride requested";
    public static final String HTML_TEXT_TEMPLATE = "<h>%s</h>";
    public static final String ACCEPTED_RIDE_MESSAGE_TEMPLATE =
            """
                    driverId: %s;
                    rideId: %s;
                    passengerId: %s;
                    car license plate: %s;
                    """;
    public static final String EMAIL_CONFIRMATION_MESSAGE_TEMPLATE =
            """
                    token: %s.
                    """;
    public static final String RIDE_ACCEPTED_SUBJECT = "Ride accepted";
    public static final String EMAIL_CONFIRMATION_SUBJECT = "Email confirmation";
    public static final String FINISHED_RIDE_MESSAGE_TEMPLATE =
            """
                    driverId: %s;
                    rideId: %s;
                    passengerId: %s;
                    """;
    public static final String RIDE_FINISHED_SUBJECT = "Ride finished";

    private final EmailSenderService emailSenderService;

    private final UserEmailDefinerService userEmailDefinerService;

    @Override
    public void emailConfirmationTemplate(EmailConfirmationDto emailConfirmationDto) {
        emailSenderService.trySendEmail(
                emailConfirmationDto.email(),
                EMAIL_CONFIRMATION_SUBJECT,
                EMAIL_CONFIRMATION_MESSAGE_TEMPLATE.formatted(emailConfirmationDto.token())
        );
    }

    @Override
    public void requestRideTemplate(RequestRideDto requestRideDto) {
        List<String> emails = userEmailDefinerService.defineRecentDriversEmails();
        String message = REQUESTED_RIDE_MESSAGE_TEMPLATE.formatted(
                requestRideDto.pickUpLocation(),
                requestRideDto.dropOffLocation(),
                requestRideDto.price(),
                requestRideDto.tariff(),
                requestRideDto.paymentMethod(),
                requestRideDto.distance(),
                requestRideDto.rideId(),
                requestRideDto.passengerId()
        );
        emails.forEach(driverEmail ->
                emailSenderService.trySendEmail(
                        driverEmail,
                        RIDE_REQUESTED_SUBJECT,
                        HTML_TEXT_TEMPLATE.formatted(message)
                )
        );
    }

    @Override
    public void acceptRideTemplate(AcceptedRideDto acceptedRideDto) {
        String email = userEmailDefinerService.definePassengerEmail(acceptedRideDto.passengerId());
        String message = ACCEPTED_RIDE_MESSAGE_TEMPLATE.formatted(
                acceptedRideDto.driverId(),
                acceptedRideDto.rideId(),
                acceptedRideDto.passengerId(),
                acceptedRideDto.carLicensePlate()
        );
        emailSenderService.trySendEmail(
                email,
                RIDE_ACCEPTED_SUBJECT,
                HTML_TEXT_TEMPLATE.formatted(message)
        );
    }

    @Override
    public void finishRideTemplate(FinishedRideDto finishedRideDto) {
        String email = userEmailDefinerService.definePassengerEmail(finishedRideDto.passengerId());
        String message = FINISHED_RIDE_MESSAGE_TEMPLATE.formatted(
                finishedRideDto.driverId(),
                finishedRideDto.rideId(),
                finishedRideDto.passengerId()
        );
        emailSenderService.trySendEmail(
                email,
                RIDE_FINISHED_SUBJECT,
                HTML_TEXT_TEMPLATE.formatted(message)
        );
    }

}
