package com.artsem.api.authservice.service;

public interface JwtService {

    String getEmailFromToken(String token);

    String generateEmailConfirmationToken(String email);

}
