package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService implements AbstractService<Category> {
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepo.getOne(id);
    }

    @Override
    public Category add(Category category) {
        return categoryRepo.saveAndFlush(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepo.saveAndFlush(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }
}
