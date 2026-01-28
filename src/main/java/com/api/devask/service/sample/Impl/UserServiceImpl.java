package com.api.devask.service.sample.Impl;

import com.api.devask.domain.sample.User;
import com.api.devask.repository.sample.UserRepository;
import com.api.devask.service.sample.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
}
