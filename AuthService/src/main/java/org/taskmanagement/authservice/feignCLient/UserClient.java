package org.taskmanagement.authservice.feignCLient;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.taskmanagement.authservice.dto.servicedto.request.CreateUserAccount;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;

@FeignClient(value = "USER-SERVICE", path = "/user-app/api/v1/users")
public interface UserClient {
    @PostMapping("/create")
     ResponseEntity<UserResponse> createNewUserAccount(@RequestBody  CreateUserAccount newUserDetails);
}
