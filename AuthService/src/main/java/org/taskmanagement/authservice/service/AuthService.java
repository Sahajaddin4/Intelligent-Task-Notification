package org.taskmanagement.authservice.service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.Repository.AuthRepository;
import org.taskmanagement.authservice.dto.request.LoginRequestDto;
import org.taskmanagement.authservice.dto.request.SignupRequestDto;
import org.taskmanagement.authservice.dto.response.AuthResponse;
import org.taskmanagement.authservice.dto.response.LoginResponse;
import org.taskmanagement.authservice.dto.servicedto.request.CreateUserAccount;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;
import org.taskmanagement.authservice.entity.Auth;
import org.taskmanagement.authservice.feignCLient.UserClient;
import org.taskmanagement.authservice.mapper.AuthMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup( SignupRequestDto signupRequestDto) {
        CreateUserAccount newUserDetails = authMapper.toCreateUserAccountUserService(signupRequestDto);
        ResponseEntity<UserResponse> userServiceResponse = userClient.createNewUserAccount(newUserDetails);
        System.out.println("user service response: " + userServiceResponse);
        Auth newAuthData = authMapper.toAuthFromUserServiceResponse(userServiceResponse.getBody());
        newAuthData.setPassword(passwordEncoder.encode(signupRequestDto.password()));
        Auth savedData = authRepository.save(newAuthData);
        System.out.println("New Auth Data: " + savedData);
        String token = jwtService.generateToken(savedData);
        return  new AuthResponse(token, HttpStatus.OK.value(),"Account created successfully.",userServiceResponse.getBody());

    }

    public LoginResponse login( LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(),loginRequestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Auth authDetails = (Auth) authentication.getPrincipal();
        String token = jwtService.generateToken(authDetails);
        return new LoginResponse(HttpStatus.OK.value(), "Login successful.",token,authDetails.getEmail(),authDetails.getId(), LocalDateTime.now());
    }
}
