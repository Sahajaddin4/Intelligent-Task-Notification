package org.taskmanagement.userservice.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDetailsDto extends ApiResponse {
     private String name;
     private String email;
     private String phone;
     private Long id;
}
