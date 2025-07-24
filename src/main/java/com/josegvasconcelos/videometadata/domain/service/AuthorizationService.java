package com.josegvasconcelos.videometadata.domain.service;

import com.josegvasconcelos.videometadata.application.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new InvalidCredentialsException("Invalid username or password")
        );
    }
}
