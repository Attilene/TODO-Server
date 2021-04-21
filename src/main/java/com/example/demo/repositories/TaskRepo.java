package com.example.demo.repositories;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findAllByUserIdAndCategoriesId(Long userId, Long categoryId);

    List<Task> findAllByUser(User user);
}
