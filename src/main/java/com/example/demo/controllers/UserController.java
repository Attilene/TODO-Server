package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserRepo userRepo) {
        this.userService = new UserService(userRepo);
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/users/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userService.getByLogin(login);
    }

    @PostMapping("/users")
    public User add(@Valid @RequestBody User user) {
        Date date = new Date();
        user.setCreated_at(date);
        user.setUpdated_at(date);
        return userService.add(user);
    }

    @PutMapping("/users/{userId}")
    public User update(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
        User user = userService.getById(userId);
        user.setUpdated_at(new Date());
        user.setLogin(userRequest.getLogin());
        return userService.update(user);
    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userService.delete(userService.getById(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
