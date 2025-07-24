package com.josegvasconcelos.videometadata.domain.service;

import com.josegvasconcelos.videometadata.application.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.domain.entity.User;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }
}
