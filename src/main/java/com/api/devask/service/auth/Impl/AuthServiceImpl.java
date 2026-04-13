package com.api.devask.service.auth.Impl;

import com.api.devask.domain.user.User;
import com.api.devask.repository.user.UserRepository;
import com.api.devask.service.auth.AuthService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Override
    @Transactional
    public void signUp(User user) {
        // 2026.04.13 비밀번호 암호화 처리
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 2026.01.28 user 정보 저장
        userRepository.save(user);
    }
}
