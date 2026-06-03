package org.taskmanagement.userservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.scheduling.config.Task;
import org.taskmanagement.userservice.dto.task.TaskDetailsDto;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDetailsDto toTaskDetailsDto(Task task);
}
