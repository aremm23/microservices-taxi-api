package com.artsem.api.authservice.service.impl;

import com.artsem.api.authservice.service.JwtService;
import com.artsem.api.authservice.util.ExceptionKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String CLAIM_TYPE = "type";
    public static final String CLAIM_TYPE_VALUE = "email_confirmation";

    private final int jwtExpireMin;

    private final String secretKey;

    public JwtServiceImpl(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expire}") int jwtExpireMin
    ) {
        this.secretKey = secretKey;
        this.jwtExpireMin = jwtExpireMin;
    }

    public String generateEmailConfirmationToken(String email) {
        Date expirationDate = Date.from(Instant.now().plus(jwtExpireMin, ChronoUnit.MINUTES));

        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(email)
                .claim(CLAIM_TYPE, CLAIM_TYPE_VALUE)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    private Claims validateAndGetClaims(String token) {
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (!CLAIM_TYPE_VALUE.equals(claims.get(CLAIM_TYPE))) {
            throw new IllegalArgumentException(ExceptionKeys.INVALID_TOKEN_TYPE);
        }

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException(ExceptionKeys.TOKEN_EXPIRED);
        }

        return claims;
    }

    public String getEmailFromToken(String token) {
        Claims claims = validateAndGetClaims(token);
        return claims.getSubject();
    }
}