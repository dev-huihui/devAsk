package com.api.devask.service.sample;

import com.api.devask.domain.sample.User;
import com.api.devask.repository.sample.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

//    public User save(String name) {
//        return userRepository.save(new User(name));
//    }
}
