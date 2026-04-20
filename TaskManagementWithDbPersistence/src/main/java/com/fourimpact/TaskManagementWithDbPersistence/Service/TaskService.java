package com.fourimpact.TaskManagementWithDbPersistence.Service;

import com.fourimpact.TaskManagementWithDbPersistence.DTO.CreateTaskRequest;
import com.fourimpact.TaskManagementWithDbPersistence.DTO.TaskResponse;
import com.fourimpact.TaskManagementWithDbPersistence.Exception.ResourceNotFoundException;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Category;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Task;
import com.fourimpact.TaskManagementWithDbPersistence.Model.User;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.CategoryRepository;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.TaskRepository;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Create
    public TaskResponse createTask(CreateTaskRequest request){
       // Task can be created even if no user assigned
        User user = null;
        if(request.getUserId() != null){
            user = userRepository.findById(request.getUserId()).orElseThrow(
                    () -> new ResourceNotFoundException("user", request.getUserId()));
        }

        // Task can be created even if no category assigned
        Category category = null;
        if(request.getCategoryId() != null){
            category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("category", request.getCategoryId()));

        }

        Task task = new Task(request.getTitle(),request.getDescription(),request.getStatus(),request.getPriority());
        task.setUser(user);
        task.setCategory(category);
        return toResponse(taskRepository.save(task));
    }

    // Helper
    public TaskResponse toResponse(Task task){
        String username =task.getUser() != null ? task.getUser().getUsername() : null;
        String categoryName = task.getCategory() != null ? task.getCategory().getName() : null;
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(),task.getStatus(),task.getPriority(),task.getCreatedAt(),username,categoryName);
    }
}
