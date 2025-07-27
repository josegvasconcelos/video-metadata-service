package com.josegvasconcelos.videometadata.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.josegvasconcelos.videometadata.application.exception.TokenGenerationException;
import com.josegvasconcelos.videometadata.application.exception.TokenValidationException;
import com.josegvasconcelos.videometadata.application.service.JWTService;
import com.josegvasconcelos.videometadata.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final String ISSUER = "video-metadata-service";
    private static final String USERNAME_CLAIM = "username";
    private static final String ROLE_CLAIM = "role";

    @Override
    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getId())
                    .withClaim(USERNAME_CLAIM, user.getUsername())
                    .withClaim(ROLE_CLAIM, user.getRole().getRole())
                    .withExpiresAt(generateExpirationInstant())
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Error while generating JWT Token for user: {}", user.getUsername());
            throw new TokenGenerationException(e.getMessage());
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            log.error("Error while validating JWT Token");
            throw new TokenValidationException(e.getMessage());
        }
    }

    private Instant generateExpirationInstant() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
