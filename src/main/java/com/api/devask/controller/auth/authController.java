package com.api.devask.controller.auth;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginResponseDTO;
import com.api.devask.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class authController {
    @Autowired
    private AuthService authService;

    // 2026.04.13 회원가입(사용자 추가 by 사용자)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        authService.signUp(user);
        return ResponseEntity.ok("success");
    }

    // 2026.04.17 로그인 기능추가
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody User user) {
        LoginResponseDTO loginResponseDTO = authService.login(user);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
