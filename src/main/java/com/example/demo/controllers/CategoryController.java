package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryRepo categoryRepo) {
        this.categoryService = new CategoryService(categoryRepo);
    }

    @GetMapping("/categories")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/categories/{categoryId}")
    public Category getById(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PostMapping("/categories")
    public Category add(@Valid @RequestBody Category category) {
        Date date = new Date();
        category.setCreated_at(date);
        category.setUpdated_at(date);
        return categoryService.add(category);
    }

    @PutMapping("/categories/{categoryId}")
    public Category update(@PathVariable Long categoryId, @Valid @RequestBody Category categoryRequest) {
        Category category = categoryService.getById(categoryId);
        category.setName(categoryRequest.getName());
        category.setUpdated_at(new Date());
        return categoryService.update(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryService.getById(categoryId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
