package org.taskmanagement.taskservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskmanagement.taskservice.dto.DefaultApiResponse;
import org.taskmanagement.taskservice.dto.request.TaskDetailsDto;
import org.taskmanagement.taskservice.dto.feignresponse.UserDetailsDto;
import org.taskmanagement.taskservice.dto.message.RemindUserForTaskDto;
import org.taskmanagement.taskservice.dto.request.UpdateTaskDetails;
import org.taskmanagement.taskservice.entity.Task;
import org.taskmanagement.taskservice.feignclient.UserClient;
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
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final MessageProducer messageProducer;
    private final UserClient  userClient;
    public List<TaskDetailsDto> createNewTask(TaskDetailsDto task,Long userId) {
        Task newTask = taskMapper.toTask(task);
        taskRepository.save(newTask);
        UserDetailsDto user = userClient.fetchUserDetails(userId).getBody();
        log.error(user.getEmail());
        if(user == null) {
            throw new RuntimeException("User is not found.Please check your id: " + userId);
        }
        messageProducer.remindUserToComplete(new RemindUserForTaskDto(user.getEmail(),user.getName(),task.name()));
        return this.fetchTasksByUserId(userId);
    }

    @Cacheable(value = "tasks", key = "#userId")
    public List<TaskDetailsDto> fetchTasksByUserId(Long userId) {
          List<TaskDetailsDto> tasksLists = new ArrayList<>();
          List<Task> allTasks = taskRepository.findTop40ByUserIdOrderByUpdatedAtDesc(userId);
          if(!allTasks.isEmpty()){
              allTasks.forEach(task -> {
                  tasksLists.add(taskMapper.toTaskDetailsDto(task));
              });
          }
          return  tasksLists;
    }
    @Cacheable(value = "tasksOfDay", key = "#userId")
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

    @Caching(evict = {
            @CacheEvict(value = "tasks",key = "#userId"),
            @CacheEvict(value = "tasksOfDay",key = "#userId")
    })
    public DefaultApiResponse deleteTaskById(Long userId, Long taskId) {
        int row = taskRepository.deleteByIdAndUserId(taskId,userId);
        if(row == 0){
            return new DefaultApiResponse("No task found with this Id", HttpStatus.NOT_FOUND,LocalDateTime.now());
        }
        return new DefaultApiResponse("Task has been deleted", HttpStatus.OK,LocalDateTime.now());
    }

    @Caching(evict = {
            @CacheEvict(value = "tasks",key = "#userId"),
            @CacheEvict(value = "tasksOfDay",key = "#userId")
    })
    @Transactional
    public DefaultApiResponse updateTaskStatus(Long userId, Long taskId, String taskStatus) {
        log.error("Updating status of task status {}", taskStatus);
        Task fetchTask = taskRepository.findByIdAndUserId(taskId,userId).orElseThrow(()-> new TaskNotFound("Task not found with id " + taskId));
        fetchTask.setStatus(TaskStatus.fromString(taskStatus));
        return  new DefaultApiResponse("Task has been updated", HttpStatus.OK,LocalDateTime.now());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "tasks",key = "#userId"),
                    @CacheEvict(value = "tasksOfDay",key = "#userId")
            }
    )
    @Transactional
    public TaskDetailsDto updateTaskDetails(Long userId, UpdateTaskDetails taskDetails) {
        Task fetchTask = taskRepository.findByIdAndUserId(taskDetails.taskId(),userId).orElseThrow(()-> new TaskNotFound("Task not found with id " + taskDetails.taskId()));
        taskMapper.toTaskFromUpdateTask(taskDetails, fetchTask);
        return  taskMapper.toTaskDetailsDto(fetchTask);
    }
}
