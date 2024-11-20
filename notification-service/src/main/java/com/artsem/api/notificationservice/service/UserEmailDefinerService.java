package com.artsem.api.notificationservice.service;

import java.util.List;

public interface UserEmailDefinerService {
    String definePassengerEmail(String passengerId);

    String defineDriverEmail(String driverId);

    List<String> defineRecentDriversEmails();
}
