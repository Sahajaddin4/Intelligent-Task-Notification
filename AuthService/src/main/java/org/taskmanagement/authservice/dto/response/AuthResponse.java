package org.taskmanagement.authservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse extends ApiResponse{
    private String token;
    private UserResponse user;
    private LocalDateTime lastLogin;
    public AuthResponse(String token, int statusCode, String message, UserResponse user) {
        super(statusCode, message);
        this.token = token;
        this.lastLogin = LocalDateTime.now();
        this.user = user;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = LocalDateTime.now();
    }
}
