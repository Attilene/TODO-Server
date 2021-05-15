package com.example.demo.dbms.repositories;

import com.example.demo.dbms.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findCategoryById(Long id);
}