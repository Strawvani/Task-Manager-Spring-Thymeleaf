package com.fourimpact.TaskManagementWithDbPersistence.Controller;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskPriority;
import com.fourimpact.TaskManagementWithDbPersistence.Service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
