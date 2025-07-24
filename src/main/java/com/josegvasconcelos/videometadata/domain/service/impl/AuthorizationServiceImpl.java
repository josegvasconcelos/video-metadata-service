package com.josegvasconcelos.videometadata.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements UserDetailsService, AuthorizationService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new InvalidCredentialsException("Invalid username or password")
        );
    }
}
