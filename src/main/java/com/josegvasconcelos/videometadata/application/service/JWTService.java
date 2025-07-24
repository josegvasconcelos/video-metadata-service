package com.josegvasconcelos.videometadata.application.service;

import com.josegvasconcelos.videometadata.domain.entity.User;

public interface JWTService {
    String generateToken(User user);
    String validateToken(String token);
}
