package org.taskmanagement.authservice.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.Repository.AuthRepository;
import org.taskmanagement.authservice.dto.message.SignupExchangeData;
import org.taskmanagement.authservice.dto.request.LoginRequestDto;
import org.taskmanagement.authservice.dto.request.SignupRequestDto;
import org.taskmanagement.authservice.dto.response.AuthResponse;
import org.taskmanagement.authservice.dto.response.LoginResponse;
import org.taskmanagement.authservice.dto.servicedto.request.CreateUserAccount;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;
import org.taskmanagement.authservice.entity.Auth;
import org.taskmanagement.authservice.exception.custom.UserAlreadyExistsException;
import org.taskmanagement.authservice.feignCLient.UserClient;
import org.taskmanagement.authservice.mapper.AuthMapper;
import org.taskmanagement.authservice.status.AccountStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final MessageProducer messageProducer;

    public AuthResponse signup( SignupRequestDto signupRequestDto) {
        if (authRepository.existsByEmail(signupRequestDto.email())) throw new UserAlreadyExistsException("User already exists with email: " +  signupRequestDto.email());
        CreateUserAccount newUserDetails = authMapper.toCreateUserAccountUserService(signupRequestDto);
         messageProducer.sendDetailsToUserService(newUserDetails);
//        ResponseEntity<UserResponse> userServiceResponse = userClient.createNewUserAccount(newUserDetails);
//       log.error("user service response: " + userServiceResponse);
//        Auth newAuthData = authMapper.toAuthFromUserServiceResponse(userServiceResponse.getBody());
        Auth newAuthData = authMapper.toAuth(signupRequestDto);
        newAuthData.setAccountStatus(AccountStatus.ACTIVE.name());
        newAuthData.setPassword(passwordEncoder.encode(signupRequestDto.password()));
        Auth savedData = authRepository.save(newAuthData);
        String token = jwtService.generateToken(savedData);
        messageProducer.sendSuccessSignUpMessage(new SignupExchangeData(signupRequestDto.name(),savedData.getEmail()));
//        return  new AuthResponse(token, HttpStatus.OK.value(),"Account created successfully.",userServiceResponse.getBody());
        return  new AuthResponse(token, HttpStatus.OK.value(),"Account created successfully.");
    }

    public LoginResponse login( LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(),loginRequestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        messageProducer.sendSuccessSignUpMessage(new SignupExchangeData(loginRequestDto.email().substring(0,5),loginRequestDto.email()));
        Auth authDetails = (Auth) authentication.getPrincipal();
        String token = jwtService.generateToken(authDetails);
        return new LoginResponse(HttpStatus.OK.value(), "Login successful.",token,authDetails.getEmail(),authDetails.getId(), LocalDateTime.now());
    }
}
