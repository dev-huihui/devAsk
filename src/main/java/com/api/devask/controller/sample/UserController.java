package com.api.devask.controller.sample;

import com.api.devask.domain.sample.User;
import com.api.devask.service.sample.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public User createUser(@RequestParam String name) {
//        return userService.save(name);
//    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
}
