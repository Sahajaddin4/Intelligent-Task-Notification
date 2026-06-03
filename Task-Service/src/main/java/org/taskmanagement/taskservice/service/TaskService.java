package org.taskmanagement.taskservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskmanagement.taskservice.dto.DefaultApiResponse;
import org.taskmanagement.taskservice.dto.TaskDetailsDto;
import org.taskmanagement.taskservice.entity.Task;
import org.taskmanagement.taskservice.globalexception.customException.TaskNotFound;
import org.taskmanagement.taskservice.mapper.TaskMapper;
import org.taskmanagement.taskservice.repository.TaskRepository;
import org.taskmanagement.taskservice.statustype.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public List<TaskDetailsDto> createNewTask(TaskDetailsDto task,Long userId) {
        Task newTask = taskMapper.toTask(task);
        taskRepository.save(newTask);
        return this.fetchTasksByUserId(userId);
    }

    public List<TaskDetailsDto> fetchTasksByUserId(Long userId) {
          List<TaskDetailsDto> tasksLists = new ArrayList<>();
          List<Task> allTasks = taskRepository.fetchTasksByUserId(userId).orElse(new ArrayList<>());
          if(!allTasks.isEmpty()){
              allTasks.forEach(task -> {
                  tasksLists.add(taskMapper.toTaskDetailsDto(task));
              });
          }

          return  tasksLists;
    }

    public List<TaskDetailsDto> fetchTodayTasksOfUser(Long userId) {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        List<TaskDetailsDto> tasksLists = new ArrayList<>();
        List<Task> tasks = taskRepository.fetchTodayTasks(userId,tomorrow).orElse(new ArrayList<>());
        if(!tasks.isEmpty()){
            tasks.forEach(task -> {
                tasksLists.add(taskMapper.toTaskDetailsDto(task));
            });
        }
        return  tasksLists;
    }

    public DefaultApiResponse deleteTaskById(Long userId, Long taskId) {
        int row = taskRepository.deleteByIdAndUserId(taskId,userId);
        if(row == 0){
            return new DefaultApiResponse("No task found with this Id", HttpStatus.NOT_FOUND,LocalDateTime.now());
        }
        return new DefaultApiResponse("Task has been deleted", HttpStatus.OK,LocalDateTime.now());
    }


    @Transactional
    public DefaultApiResponse updateTaskStatus(Long userId, Long taskId, TaskStatus taskStatus) {
        Task fetchTask = taskRepository.findById(taskId).orElseThrow(()-> new TaskNotFound("Task not found with id " + taskId));
        fetchTask.setStatus(taskStatus);
        return  new DefaultApiResponse("Task has been updated", HttpStatus.OK,LocalDateTime.now());
    }
}
