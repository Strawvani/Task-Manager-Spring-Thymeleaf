package com.fourimpact.TaskManagementWithDbPersistence.Controller;

import com.fourimpact.TaskManagementWithDbPersistence.DTO.CreateTaskRequest;
import com.fourimpact.TaskManagementWithDbPersistence.DTO.TaskResponse;
import com.fourimpact.TaskManagementWithDbPersistence.Service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    // Read Task
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<TaskResponse>> getTasksByStatus(
            @PathVariable String status,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.getTaskByStatus(status,pageable));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<TaskResponse>> getTasksByUserId(
            @RequestParam Long id,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "createdAt") String sortBy,
            @RequestParam (defaultValue = "desc") String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.ok(taskService.getTasksByUserId(id,pageable));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody CreateTaskRequest request){
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
