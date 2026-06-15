package org.taskmanagement.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taskmanagement.authservice.dto.servicedto.response.UserResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse extends ApiResponse{
    private String token;
//    private UserResponse user;
    @JsonFormat(pattern = "dd MMM yyyy 'at' hh:mm a")
    private LocalDateTime lastLogin;
    public AuthResponse(String token, int statusCode, String message) {
        super(statusCode, message);
        this.token = token;
        this.lastLogin = LocalDateTime.now();
//        this.user = user;
    }

//    public void setLastLogin(LocalDateTime lastLogin) {
//        this.lastLogin = LocalDateTime.now();
//    }
}
