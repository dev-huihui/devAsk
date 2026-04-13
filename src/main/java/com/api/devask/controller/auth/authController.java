package com.api.devask.controller.auth;

import com.api.devask.domain.user.User;
import com.api.devask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class authController {

    private final UserService userService;

    // 2026.04.13 회원가입(사용자 추가 by 사용자)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("success");
    }

    // TODO: 로그인 로직 추가
}
