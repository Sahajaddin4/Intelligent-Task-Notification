package org.taskmanagement.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.taskmanagement.taskservice.entity.Task;
import org.taskmanagement.taskservice.statustype.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    int deleteByIdAndUserId( Long taskId,Long userId);

    @Query("""
    Select t from Task t where t.userId = :userId and t.status = TaskStatus.ACTIVE \s
""")
    Optional<List<Task>> fetchTasksByUserId(Long userId);
    @Query("""
Select t from Task t where t.userId = :userId and  t.status = TaskStatus.ACTIVE  and t.endTime < :tomorrow
""")
    Optional<List<Task>> fetchTodayTasks(Long userId, LocalDateTime tomorrow);
    
//    @Query("""
// Update t from Task u where t.userId = :userId and t.id = :taslId
//""")
//    Boolean updateTaskStatus(Long userId,Long taskId, TaskStatus taskStatus);
}