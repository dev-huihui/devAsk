package com.api.devask.controller.sample;

import com.api.devask.domain.sample.User;
import com.api.devask.service.sample.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // 2026.01.28 사용자 정보 추가하는 controller
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("success");
    }
}
