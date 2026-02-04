package com.api.devask.service.user.Impl;

import com.api.devask.domain.user.User;
import com.api.devask.repository.user.UserRepository;
import com.api.devask.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Transactional
    @Override
    public void addUser(User user) {
        // TODO: 비밀번호 암호화 처리하도록 로직 추가 필요
        
        // 2026.01.28 user 정보 저장
        userRepository.save(user);
    }

    // 2026.02.05 user 정보 수정하는 메소드
    @Transactional
    @Override
    public void modifyUser(User user) {
        /* 2026.02.05 userID로 정보 찾기
         * Optional 안에 값이 있으면 그 값을 꺼내고, 없으면 예외를 던져라 */
        User changeUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user 없음"));
        
        // 2026.02.05 user 이름 값이 있을 경우에만
        if (user.getUserName() != null) {
            changeUser.setUserName(user.getUserName());
        }

        // 2026.02.05 user 비밀번호 값이 있을 경우에만
        if (user.getPassword() != null) {
            // TODO: 비밀번호 암호화하는 로직 추가 필요
            
            changeUser.setPassword(user.getPassword());
        }

        // 2026.02.05 user 이메일 값이 있을 경우에만
        if (user.getEmail() != null) {
            changeUser.setEmail(user.getEmail());
        }
    }
}
