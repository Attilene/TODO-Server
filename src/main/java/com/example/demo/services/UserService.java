package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements AbstractService<User> {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) { this.userRepo = userRepo; }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.getOne(id);
    }

    public User getByLogin(String login) {
        return userRepo.findUserByLogin(login);
    }

    @Override
    public User add(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public User update(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }
}
