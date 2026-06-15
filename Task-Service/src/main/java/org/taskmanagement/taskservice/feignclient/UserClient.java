package org.taskmanagement.taskservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.taskmanagement.taskservice.dto.feignresponse.UserDetailsDto;

@FeignClient(name = "USER-SERVICE",path = "user-app/api/users")
public interface UserClient {
//
    @GetMapping("/fetch-user/{userId}")
    public ResponseEntity<UserDetailsDto>  fetchUserDetails(@PathVariable Long userId);
}
