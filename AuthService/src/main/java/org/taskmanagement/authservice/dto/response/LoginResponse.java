package org.taskmanagement.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse extends ApiResponse {
    private String token;
    private String email;
    private Long userId;
    private LocalDateTime lastLogin;

    public LoginResponse(int statusCode, String message, String token, String email, Long userId, LocalDateTime lastLogin) {
        super(statusCode, message);
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.lastLogin = lastLogin;
    }
}
