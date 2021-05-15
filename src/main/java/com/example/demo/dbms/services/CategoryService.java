package com.example.demo.dbms.services;

import com.example.demo.exceptions.ResourceIsExistException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.dbms.models.Category;
import com.example.demo.dbms.repositories.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryService{
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    public Category getById(Long id) throws ResourceNotFoundException {
        return categoryRepo.findCategoryById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
    }

    public Category add(Category category) throws ResourceIsExistException {
        if (categoryRepo.existsByName(category.getName()))
            throw new ResourceIsExistException("Category is already exist!");
        Date date = new Date();
        category.setCreated_at(date);
        category.setUpdated_at(date);
        return categoryRepo.saveAndFlush(category);
    }

    public Category update(Long categoryId, Category categoryRequest) {
        Category category = categoryRepo.findCategoryById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        if ((categoryRepo.existsByName(categoryRequest.getName()) &&
                !category.getName().equals(categoryRequest.getName())))
            throw new ResourceIsExistException("Category is already exist!");
        category.setUpdated_at(new Date());
        category.setName(categoryRequest.getName());
        return categoryRepo.saveAndFlush(category);
    }

    public void delete(Category category) {
        categoryRepo.findCategoryById(category.getId()).ifPresent(categoryRepo::delete);

    }
}
