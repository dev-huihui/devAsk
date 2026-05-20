package com.api.devask.controller.auth;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginRequestDTO;
import com.api.devask.dto.auth.LoginResponseDTO;
import com.api.devask.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class authController {

    private final AuthService authService;

    // 2026.05.20 ID 중복체크 API
    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkUserId(@RequestParam String userId) {
        return ResponseEntity.ok(authService.checkId(userId));
    }

    // 2026.04.13 회원가입(사용자 추가 by 사용자)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        authService.signUp(user);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 2026.04.17 로그인 기능추가
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
}
