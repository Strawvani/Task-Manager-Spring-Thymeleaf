package com.fourimpact.TaskManagementWithDbPersistence.DTO;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskPriority;
import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskStatus;

import java.util.List;

public class CreateTaskRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    private Long userId;
    private Long categoryId;
    private List<Long> tag_Id;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryID) {
        this.categoryId = categoryID;
    }

    public List<Long> getTag_ID() {
        return tag_Id;
    }

    public void setTag_ID(List<Long> tag_ID) {
        this.tag_Id = tag_ID;
    }
}
