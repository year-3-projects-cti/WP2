package com.example.crm.controllers;

import com.example.crm.models.User;
import com.example.crm.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String role) {
        if (role != null) {
            return userService.getAllUsers().stream()
                .filter(u -> u.getRole().equalsIgnoreCase(role))
                .toList();
        }
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return userService.getAllUsers().stream()
            .filter(u -> u.getEmail().equals(loginRequest.getEmail())
                    && u.getPassword().equals(loginRequest.getPassword()))
            .findFirst()
            .orElse(null);
    }
}
