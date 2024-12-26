package com.artsem.api.reviewservice.security;

import com.artsem.api.reviewservice.exceptions.InternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class KeycloakTokenProvider {

    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXTRACTING_TOKEN_EXCEPTION_MESSAGE = "Error occurred while extracting token";

    private final String clientId;
    private final String clientSecret;
    private final String authServerUrl;
    private final RestTemplate restTemplate;

    public KeycloakTokenProvider(
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret,
            @Value("${keycloak.auth-server-url}") String authServerUrl,
            RestTemplate restTemplate
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authServerUrl = authServerUrl;
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(createRequestBody(), new HttpHeaders());
        ResponseEntity<Map> response = restTemplate.postForEntity(authServerUrl, request, Map.class);
        return extractAccessToken(response);
    }

    private MultiValueMap<String, String> createRequestBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE, CLIENT_CREDENTIALS);
        body.add(CLIENT_ID, clientId);
        body.add(CLIENT_SECRET, clientSecret);
        return body;
    }

    private String extractAccessToken(ResponseEntity<Map> response) {
        Map<?, ?> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey(ACCESS_TOKEN)) {
            return responseBody.get(ACCESS_TOKEN).toString();
        }
        throw new InternalServiceException(EXTRACTING_TOKEN_EXCEPTION_MESSAGE);
    }
}
