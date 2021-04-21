package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.TaskRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.CategoryService;
import com.example.demo.services.TaskService;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    private final UserService userService;

    private final CategoryService categoryService;

    public TaskController(TaskRepo taskRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.taskService = new TaskService(taskRepo);
        this.categoryService = new CategoryService(categoryRepo);
        this.userService = new UserService(userRepo);
    }

    @GetMapping("/tasks")
    public List<Task> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/tasks/{taskId}")
    public Task getById(@PathVariable long taskId) {
        return taskService.getById(taskId);
    }

    @GetMapping("/users/{userId}/tasks")
    public List<Task> getByUser(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return taskService.getByUserId(user);
    }

    @GetMapping("/users/{userId}/category/{categoryId}/tasks")
    public List<Task> getByCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        User user = userService.getById(userId);
        return taskService.getByUserAndCategory(user, category);
    }

    @PostMapping("/users/{userId}/tasks")
    public Task create(@PathVariable long userId, @Valid @RequestBody Task task) {
        User user = userService.getById(userId);
        if (user != null) {
            Date date = new Date();
            user.setCreated_at(date);
            user.setUpdated_at(date);
            user.addTask(task);
            return task;
        }
        return null;
    }

    @PutMapping("/tasks/{taskId}/{categoryId}")
    public Task addCategory(@PathVariable long taskId, @PathVariable long categoryId) {
        Task task = taskService.getById(taskId);
        Category category = categoryService.getById(categoryId);
        if (task != null && category != null) {
            task.addCategory(category);
            task.setUpdated_at(new Date());
            taskService.update(task);
            return task;
        }
        return null;
    }

    @PutMapping("/users/{userId}/tasks/{taskId}")
    public Task updateTask(@PathVariable long userId, @PathVariable long taskId, @Valid @RequestBody Task taskRequest) {
        Task task = taskService.getById(taskId);
        User user = userService.getById(userId);
        if (task != null && user != null) {
            task.setName(taskRequest.getName());
            task.setDescription(taskRequest.getDescription());
            task.setOperation_date(taskRequest.getOperation_date());
            task.setUpdated_at(new Date());
            task.setComplete(taskRequest.getComplete());
            task.setUser(user);
            return task;
        }
        return null;
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<?> delete(@PathVariable long userId, @PathVariable long taskId) {
        Task task = taskService.getById(taskId);
        User user = userService.getById(userId);
        if (task != null && user != null) {
            user.removeTask(task);
            return ResponseEntity.ok().build();
        }
        return null;
    }
}
