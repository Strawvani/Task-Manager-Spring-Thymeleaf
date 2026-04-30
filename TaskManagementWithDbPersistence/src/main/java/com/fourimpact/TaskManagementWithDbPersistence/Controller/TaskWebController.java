package com.fourimpact.TaskManagementWithDbPersistence.Controller;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskPriority;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Task;
import com.fourimpact.TaskManagementWithDbPersistence.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskWebController {

    private final TaskService taskService;

    public TaskWebController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Pass a list of tasks to the template

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasksList());
        model.addAttribute("pageTitle", "All Tasks");
        return "tasks/list";   // resolves to templates/tasks/list.html
    }

    // Pass a single task by ID

    @GetMapping("/{id}")
    public String viewTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "tasks/detail";
    }

    // Passing an enum map for status counts

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tasks",      taskService.getAllTasksList());
        model.addAttribute("statusCount", taskService.countByStatus());
        model.addAttribute("priority",  TaskPriority.values());
        return "dashboard";
    }

    // Show the empty form
    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());  // empty backing object
        model.addAttribute("pageTitle", "New Task");
        model.addAttribute("priority",  TaskPriority.values());
        return "tasks/form";
    }

// Handle form submission
    @PutMapping
    public String createTask(@Valid @ModelAttribute Task task, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("pageTitle", "New Task");
            return "tasks/form";
        }
        taskService.save(task);
        return "redirect:/tasks";  // Post-Redirect-Get pattern
    }

    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Task");
        model.addAttribute("task", taskService.getTaskById(id));
        return "tasks/form";
    }

    @PutMapping("/{id}")
    public String updateTask(@Valid @ModelAttribute Task task, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Task");
            return "tasks/form";
        }
        task.setId(id);
        taskService.save(task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // JS

    @PatchMapping("/{id}/complete")
    @ResponseBody
    public ResponseEntity<Void> markComplete(@PathVariable Long id) {
        taskService.markComplete(id);
        return ResponseEntity.ok().build();

    }
}
