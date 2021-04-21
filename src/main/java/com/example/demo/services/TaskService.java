package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService implements AbstractService<Task> {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    public List<Task> getByUserId(User user) {
        return taskRepo.findAllByUser(user);
    }

    public List<Task> getByUserAndCategory(User user, Category category) {
        return taskRepo.findAllByUserIdAndCategoriesId(user.getId(), category.getId());
    }

    @Override
    public Task getById(Long id) {
        return taskRepo.getOne(id);
    }

    @Override
    public Task add(Task task) {
        return taskRepo.saveAndFlush(task);
    }

    @Override
    public Task update(Task task) {
        return taskRepo.saveAndFlush(task);
    }

    @Override
    public void delete(Task task) {
        taskRepo.delete(task);
    }
}
