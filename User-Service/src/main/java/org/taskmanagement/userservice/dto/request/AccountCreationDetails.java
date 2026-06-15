package org.taskmanagement.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationDetails implements Serializable {
    private String name;
    @Email(message = "Email is not valid.")
    private String email;
  
    private String phone;
}
