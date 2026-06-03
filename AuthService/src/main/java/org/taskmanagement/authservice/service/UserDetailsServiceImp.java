package org.taskmanagement.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.Repository.AuthRepository;
import org.taskmanagement.authservice.entity.Auth;
import org.taskmanagement.authservice.exception.custom.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        return authRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found with email: "+email));
    }
}
