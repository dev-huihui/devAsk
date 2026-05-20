package com.api.devask.service.auth;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginRequestDTO;
import com.api.devask.dto.auth.LoginResponseDTO;

public interface AuthService {
    boolean checkId(String userId);

    void signUp(User user);

    LoginResponseDTO login(LoginRequestDTO loginRequest);
}