package org.taskmanagement.taskservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE",path = "user-management-app/api")
public interface UserClient {
//
//    @GetMapping("/users/verfiy")
//    public ResponseEntity<?> boolean checkUserIsValid(Long userId);
}
