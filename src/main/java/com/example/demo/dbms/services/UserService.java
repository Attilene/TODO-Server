package com.example.demo.dbms.services;

import com.example.demo.exceptions.ResourceIsExistException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.dbms.models.User;
import com.example.demo.dbms.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) { this.userRepo = userRepo; }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) throws ResourceNotFoundException {
        return userRepo.findUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + id)
        );
    }

    public User getByLogin(String login) throws ResourceNotFoundException {
        return userRepo.findUserByLogin(login).orElseThrow(
                () -> new ResourceNotFoundException("User not found with login: " + login)
        );
    }

    public User add(User user) throws ResourceIsExistException {
        if (userRepo.existsByLogin(user.getLogin()) || userRepo.existsByEmail(user.getEmail()))
            throw new ResourceIsExistException("User is already exist!");
        Date date = new Date();
        user.setCreated_at(date);
        user.setUpdated_at(date);
        return userRepo.saveAndFlush(user);
    }

    public User authorization(User user) throws ResourceNotFoundException {
        User new_user = userRepo.findUserByLogin(user.getLogin())
                .orElseGet(() -> userRepo.findUserByEmail(user.getEmail()).
                        orElseThrow(() -> new ResourceNotFoundException("User not found")));
        if (new_user.getPassword().equals(user.getPassword()))
            return new_user;
        else
            throw new ResourceNotFoundException("User not found");
    }

    public User update(Long userId, User userRequest) throws ResourceNotFoundException {
        User user = userRepo.findUserById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        if ((userRepo.existsByLogin(userRequest.getLogin()) && !user.getLogin().equals(userRequest.getLogin()))
                || (userRepo.existsByEmail(userRequest.getEmail()) && !user.getEmail().equals(userRequest.getEmail())))
            throw new ResourceIsExistException("User is already exist!");
        user.setUpdated_at(new Date());
        user.setLogin(userRequest.getLogin());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return userRepo.saveAndFlush(user);
    }

    public void delete(User user) {
        userRepo.findUserByLogin(user.getLogin()).ifPresent(userRepo::delete);
    }
}
