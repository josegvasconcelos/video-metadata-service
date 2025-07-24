package com.josegvasconcelos.videometadata.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.josegvasconcelos.videometadata.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final String ISSUER = "video-metadata-service";
    private static final String USERNAME_CLAIM = "username";

    public String generateToken(User user) {
        var algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getId())
                .withClaim(USERNAME_CLAIM, user.getUsername())
                .withExpiresAt(generateExpirationInstant())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        var algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationInstant() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
