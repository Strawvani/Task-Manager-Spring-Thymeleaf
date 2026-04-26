package com.fourimpact.TaskManagementWithDbPersistence.Controller;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskPriority;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Task;
import com.fourimpact.TaskManagementWithDbPersistence.Service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @PostMapping
    public String createTask(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";  // Post-Redirect-Get pattern
    }

    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "tasks/form";
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id);
        taskService.save(task);
        return "redirect:/tasks";
    }
}
