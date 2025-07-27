package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.domain.entity.User;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow( () -> {
                    log.error("User not found for username: {}", username);
                    return new UserNotFoundException("User not found");
                });
    }

    @Override
    public User findById(String id) {
        log.info("Finding user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow( () -> {
                    log.error("User not found with id: {}", id);
                    return new UserNotFoundException("User not found");
                });
    }
}
