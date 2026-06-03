package org.taskmanagement.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskmanagement.userservice.dto.request.AccountCreationDetails;
import org.taskmanagement.userservice.dto.request.EmailRequestDto;
import org.taskmanagement.userservice.dto.request.UpdateUserDetailsDto;
import org.taskmanagement.userservice.dto.response.UserDetailsDto;
import org.taskmanagement.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public String check(){
        return "hi";
    }
    @PostMapping("/create")
    public ResponseEntity<UserDetailsDto> createNewUserAccount(@Valid  @RequestBody AccountCreationDetails newUserDetails){
        UserDetailsDto user = userService.createNewUserAccount(newUserDetails);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/fetch-user/{userId}")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUserDetails(userId));
    }
    @GetMapping("/fetch-user")
    public ResponseEntity<UserDetailsDto> getUserDetailsByEmail(@Valid @RequestBody EmailRequestDto request){
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUserDetails(request.email()));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody EmailRequestDto emailRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyUser(emailRequestDto.email()));
    }

    @PutMapping("/update-details/{userId}")
    public ResponseEntity<UserDetailsDto> updateUserDetails( @Valid @RequestBody UpdateUserDetailsDto userDetailsDto, @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserDetails(userDetailsDto,userId));
    }
}

