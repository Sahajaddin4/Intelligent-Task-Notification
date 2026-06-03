package org.taskmanagement.userservice.dto.task;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDetailsDto{
    String name;
    String description;
    Long userId;
    LocalDateTime endTime;
}