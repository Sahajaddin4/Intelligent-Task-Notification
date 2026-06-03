package org.taskmanagement.taskservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.taskmanagement.taskservice.statustype.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Tasks")
@SQLRestriction("deleted_at is NULL and status is TaskStatus.ACTIVE ")
@SQLDelete(sql = """
        Update tasks set deleted_at = now() where id = ?
        """)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    private TaskStatus status = TaskStatus.ACTIVE;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @Column(nullable = false)
    private Long userId;
}
