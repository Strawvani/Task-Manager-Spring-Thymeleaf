package com.fourimpact.TaskManagementWithDbPersistence.Service;

import com.fourimpact.TaskManagementWithDbPersistence.DTO.CreateTaskRequest;
import com.fourimpact.TaskManagementWithDbPersistence.DTO.TaskResponse;
import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskStatus;
import com.fourimpact.TaskManagementWithDbPersistence.Exception.ResourceNotFoundException;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Category;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Task;
import com.fourimpact.TaskManagementWithDbPersistence.Model.User;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.CategoryRepository;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.TaskRepository;
import com.fourimpact.TaskManagementWithDbPersistence.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

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

    // Read
    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(Pageable pageable){
        return taskRepository.findAll(pageable).map(this::toResponse);

    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long Id){
        Task task = taskRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("task", Id)
        );
        return toResponse(task);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> getTaskByStatus(String status, Pageable pageable){
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByStatus(taskStatus, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasksByUserId(Long Id, Pageable pageable){
        return taskRepository.findByUser_Id(Id,pageable).map(this::toResponse);
    }

    // Update
    public TaskResponse updateTask(Long Id, CreateTaskRequest request){
        Task task = taskRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("task", Id)
        );


        if(request.getUserId() != null){
            User user = userRepository.findById(request.getUserId()).orElseThrow(
                    () -> new ResourceNotFoundException("user", request.getUserId()));
            task.setUser(user);
        }

        if (request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("category", request.getCategoryId()));
            task.setCategory(category);
        }

        if (request.getTitle() != null){
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null){
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null){
            task.setStatus(request.getStatus());
        }

        if (request.getPriority() != null){
            task.setPriority(request.getPriority());
        }

        return toResponse(taskRepository.save(task));
    }

    // Delete
    public void deleteTask(Long Id){
        Task task = taskRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("task", Id)
        );
        taskRepository.delete(task);
    }

    // Helper
    public TaskResponse toResponse(Task task){
        String username =task.getUser() != null ? task.getUser().getUsername() : null;
        String categoryName = task.getCategory() != null ? task.getCategory().getName() : null;
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(),task.getStatus(),task.getPriority(),task.getCreatedAt(),username,categoryName);
    }
}
