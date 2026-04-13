package com.api.devask.service.auth;

import com.api.devask.domain.user.User;

public interface AuthService {
    void signUp(User user);
}