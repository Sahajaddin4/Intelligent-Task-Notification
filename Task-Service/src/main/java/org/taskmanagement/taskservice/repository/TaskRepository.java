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
    Select t from Task t where t.userId = :userId \s
""")
    List<Task> findTop40ByUserIdOrderByUpdatedAtDesc(Long userId);
    @Query("""
Select t from Task t where t.userId = :userId  and t.executionTime < :tomorrow
""")
    Optional<List<Task>> fetchTodayTasks(Long userId, LocalDateTime tomorrow);
    
    Optional<Task> findByIdAndUserId(Long taskId,Long userId);
}