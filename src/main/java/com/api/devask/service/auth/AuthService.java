package com.api.devask.service.auth;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginResponseDTO;

public interface AuthService {
    void signUp(User user);

    LoginResponseDTO login(User user);
}