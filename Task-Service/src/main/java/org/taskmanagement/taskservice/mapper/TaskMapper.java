package org.taskmanagement.taskservice.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.bind.annotation.Mapping;
import org.taskmanagement.taskservice.dto.TaskDetailsDto;
import org.taskmanagement.taskservice.dto.request.UpdateTaskDetails;
import org.taskmanagement.taskservice.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  Task toTask(TaskDetailsDto task);
//  @Mapping(source = "endTime",target = "endTime",dateFormat = "dd-MM-yyyy hh:mm a")
  TaskDetailsDto toTaskDetailsDto(Task task);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toTaskFromUpdateTask(UpdateTaskDetails taskDetails, @MappingTarget Task task);
}
