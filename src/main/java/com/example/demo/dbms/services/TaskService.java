package com.example.demo.dbms.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.dbms.models.Category;
import com.example.demo.dbms.models.Task;
import com.example.demo.dbms.models.User;
import com.example.demo.dbms.repositories.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    public List<Task> getByUserId(Long userId) {
        return taskRepo.findAllByUser_Id(userId);
    }

    public Task getByUserAndCategory(User user, Category category) {
        return taskRepo.findAllByUserIdAndCategoryId(user.getId(), category.getId());
    }

    public Task getById(Long id) throws ResourceNotFoundException {
        return taskRepo.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id: " + id)
        );
    }

    public Task add(User user, Category category, Task task) {
        Date date = new Date();
        task.setCreated_at(date);
        task.setUpdated_at(date);
        task.setUser(user);
        task.setCategory(category);
        return taskRepo.saveAndFlush(task);
    }

    public Task update(User user, Category category, Task task, Task taskRequest) {
        task.setUpdated_at(new Date());
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setComplete(taskRequest.getComplete());
        task.setOperation_date(taskRequest.getOperation_date());
        task.setUser(user);
        task.setCategory(category);
        return taskRepo.saveAndFlush(task);
    }

    public void delete(Task task) {
        taskRepo.findTaskById(task.getId()).ifPresent(taskRepo::delete);
    }
}
