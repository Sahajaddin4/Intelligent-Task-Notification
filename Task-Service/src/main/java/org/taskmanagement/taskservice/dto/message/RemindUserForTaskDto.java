package org.taskmanagement.taskservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RemindUserForTaskDto implements Serializable {
    private String email;
    private String name;
    private String taskName;
}
