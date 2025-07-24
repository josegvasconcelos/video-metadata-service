package com.josegvasconcelos.videometadata.domain.service;

import com.josegvasconcelos.videometadata.domain.entity.User;

public interface UserService {
    User findByUsername(String username);
    User findById(String id);
}
