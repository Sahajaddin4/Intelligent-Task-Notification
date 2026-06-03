package org.taskmanagement.userservice.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsDto extends ApiResponse {
     private String name;
     private String email;
     private String phoneNumber;
     private Long id;
}
