package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.domain.entity.User;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }
}
