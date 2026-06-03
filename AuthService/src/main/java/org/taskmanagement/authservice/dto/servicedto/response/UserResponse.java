package org.taskmanagement.authservice.dto.servicedto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    String name;
    String email;
    Long id;
    String phone;
}
