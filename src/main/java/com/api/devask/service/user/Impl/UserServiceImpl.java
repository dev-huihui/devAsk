package com.api.devask.service.user.Impl;

import com.api.devask.domain.user.User;
import com.api.devask.dto.user.UserResponseDTO;
import com.api.devask.repository.user.UserRepository;
import com.api.devask.service.user.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Override
    @Transactional
    public void addUser(User user) {
        // 2026.04.21 아이디 중복체크
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 2026.04.13 비밀번호 암호화 처리
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 2026.01.28 user 정보 저장
        userRepository.save(user);
    }

    // 2026.02.05 user 정보 수정하는 메소드
    @Override
    @Transactional
    public void modifyUser(User user) {
        /*
         * 2026.02.05 userID로 정보 찾기
         * Optional 안에 값이 있으면 그 값을 꺼내고, 없으면 예외를 던져라
         */
        User changeUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user 없음"));

        // 2026.02.05 user 이름 값이 있을 경우에만
        if (user.getUserName() != null) {
            changeUser.setUserName(user.getUserName());
        }

        // 2026.02.05 user 비밀번호 값이 있을 경우에만
        if (user.getPassword() != null) {
            // 2026.04.13 비밀번호 암호화 처리
            changeUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 2026.02.05 user 이메일 값이 있을 경우에만
        if (user.getEmail() != null) {
            changeUser.setEmail(user.getEmail());
        }
    }

    // 2026.02.08 user 정보를 조회하는 메소드
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO loadUserByUserId(String userId) {
        /*
         * 2026.02.05 userID로 정보 찾기
         * Optional 안에 값이 있으면 그 값을 꺼내고, 없으면 예외를 던져라
         */
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return UserResponseDTO.from(user);
    }

    // 2026.04.20 아이디 중복 체크하는 메소드
    @Override
    @Transactional(readOnly = true)
    public boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
