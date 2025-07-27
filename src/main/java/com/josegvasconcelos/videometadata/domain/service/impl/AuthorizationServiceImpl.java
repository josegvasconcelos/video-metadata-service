package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements UserDetailsService, AuthorizationService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        return userRepository.findByUsername(username).orElseThrow( () -> {
            log.error("User not found by username: {}", username);
            return new InvalidCredentialsException("Invalid username or password");
        });
    }
}
