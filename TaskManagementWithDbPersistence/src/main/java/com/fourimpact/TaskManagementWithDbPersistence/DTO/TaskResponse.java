package com.fourimpact.TaskManagementWithDbPersistence.DTO;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskPriority;
import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {

    // Field
    private  Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private String username;
    private String categoryName;

    // Constructor
    public TaskResponse(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime createdAt, String username, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.username = username;
        this.categoryName = categoryName;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {return username;}

    public String getCategoryName() {return categoryName;}
}
