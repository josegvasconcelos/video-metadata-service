package com.josegvasconcelos.videometadata.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.josegvasconcelos.videometadata.application.exception.TokenGenerationException;
import com.josegvasconcelos.videometadata.application.exception.TokenValidationException;
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
    private static final String ROLE_CLAIM = "role";

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
            throw new TokenGenerationException(e.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new TokenValidationException(e.getMessage());
        }
    }

    private Instant generateExpirationInstant() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
