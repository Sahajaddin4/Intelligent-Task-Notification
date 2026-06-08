package org.taskmanagement.taskservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskmanagement.taskservice.dto.TaskDetailsDto;
import org.taskmanagement.taskservice.dto.request.StatusUpdateRequest;
import org.taskmanagement.taskservice.dto.request.UpdateTaskDetails;
import org.taskmanagement.taskservice.service.TaskService;
import org.taskmanagement.taskservice.statustype.TaskStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController   {

    private final TaskService taskService;

    @PostMapping("/{userId}/create")
    public ResponseEntity<List<TaskDetailsDto>> createNewTask(@Valid @RequestBody TaskDetailsDto task, @PathVariable("userId") Long userId) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createNewTask(task,userId));
    }

    @GetMapping("{userId}/fetch-all-tasks")
    public ResponseEntity<List<TaskDetailsDto>> fetchTasksByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.fetchTasksByUserId(userId));
    }

    @GetMapping("{userId}/fetch-today-tasks")
    public ResponseEntity<List<TaskDetailsDto>> fetchTodayTasks(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.fetchTodayTasksOfUser(userId));
    }

    @DeleteMapping("{userId}/delete-task/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTaskById(userId,taskId));
    }

    @PutMapping("/{userId}/update-status/{taskId}")
    public  ResponseEntity<?> updateTaskStatus(@PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId, @RequestBody StatusUpdateRequest taskStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskStatus(userId,taskId,taskStatus.status()));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateTaskDetails(@PathVariable("userId") Long userId, @RequestBody UpdateTaskDetails taskDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskDetails(userId,taskDetails));
    }
}
