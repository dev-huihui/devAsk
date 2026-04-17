package com.api.devask.controller.user;

import com.api.devask.domain.user.User;
import com.api.devask.dto.user.UserResponseDTO;
import com.api.devask.service.user.UserService;
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

    // 2026.02.05 사용자 정보 수정하는 controller
    @PostMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody User user) {
        userService.modifyUser(user);
        return ResponseEntity.ok("success");
    }

    // 2026.02.08 사용자 정보 조회하는 controller
    @GetMapping("/{userId}/load")
    public UserResponseDTO loadUser(@PathVariable String userId) {
        return userService.loadUserByUserId(userId);
    }
}
