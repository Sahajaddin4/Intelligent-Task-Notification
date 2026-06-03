package org.taskmanagement.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationDetails {
    private String name;
    @Email(message = "Email is not valid.")
    private String email;
  
    private String phoneNumber;
}
