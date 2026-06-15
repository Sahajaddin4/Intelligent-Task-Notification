package org.taskmanagement.taskservice.dto.feignresponse;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsDto {
    private String name;
    private String email;
    private String phone;
    private Long id;
}
