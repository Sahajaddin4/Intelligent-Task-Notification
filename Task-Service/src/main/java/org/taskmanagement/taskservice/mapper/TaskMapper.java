package org.taskmanagement.taskservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.taskmanagement.taskservice.dto.TaskDetailsDto;
import org.taskmanagement.taskservice.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  Task toTask(TaskDetailsDto task);
//  @Mapping(source = "endTime",target = "endTime",dateFormat = "dd-MM-yyyy hh:mm a")
  TaskDetailsDto toTaskDetailsDto(Task task);

}
