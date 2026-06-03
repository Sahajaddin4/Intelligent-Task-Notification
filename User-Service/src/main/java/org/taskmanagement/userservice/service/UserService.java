package org.taskmanagement.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskmanagement.userservice.dto.request.AccountCreationDetails;
import org.taskmanagement.userservice.dto.request.UpdateUserDetailsDto;
import org.taskmanagement.userservice.dto.response.ApiResponse;
import org.taskmanagement.userservice.dto.response.UserDetailsDto;
import org.taskmanagement.userservice.entity.User;
import org.taskmanagement.userservice.exception.customException.UserAlreadyExistsException;
import org.taskmanagement.userservice.exception.customException.UserNotFoundException;
import org.taskmanagement.userservice.mapper.UserMapper;
import org.taskmanagement.userservice.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private boolean isUserExists(String email){
        return userRepository.existsByEmail(email);
    }
    public UserDetailsDto createNewUserAccount(AccountCreationDetails newUserDetails) {
        if(isUserExists(newUserDetails.getEmail())) throw new UserAlreadyExistsException("Already exists account with this email:"+newUserDetails.getEmail());
        User user = userMapper.toUser(newUserDetails);
        User saved = userRepository.save(user);
        UserDetailsDto userDetails = userMapper.toUserDetailsDto(saved);
        userDetails.setStatusCode(HttpStatus.CREATED);
        userDetails.setMessage("User created successfully");
        return userDetails;
    }

    public UserDetailsDto fetchUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User is not found.Please check your id: " + userId));
        UserDetailsDto userDetailsDto =  userMapper.toUserDetailsDto(user);
        userDetailsDto.setStatusCode(HttpStatus.OK);
        userDetailsDto.setMessage("User details fetched successfully");
        return userDetailsDto;
    }

    public UserDetailsDto fetchUserDetails(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User is not found.Please check your email: " + email));
        UserDetailsDto userDetailsDto =  userMapper.toUserDetailsDto(user);
        userDetailsDto.setStatusCode(HttpStatus.OK);
        userDetailsDto.setMessage("User details fetched successfully");
        return userDetailsDto;
    }

    public ApiResponse verifyUser(String email) {
         userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User is not found.Please check your email: " + email));
        return new ApiResponse(HttpStatus.OK, "User details verified successfully");
    }

    @Transactional
    public UserDetailsDto updateUserDetails(UpdateUserDetailsDto userDetailsDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User is not found.Please check your id: " + userId));
        userMapper.updateUserDetails(userDetailsDto, user);
        UserDetailsDto updatedDetails =  userMapper.toUserDetailsDto(user);
        updatedDetails.setStatusCode(HttpStatus.OK);
        updatedDetails.setMessage("User details updated successfully");
        return updatedDetails;
    }

}
