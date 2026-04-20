package com.api.devask.service.user;

import com.api.devask.domain.user.User;
import com.api.devask.dto.user.UserResponseDTO;

public interface UserService {
    void addUser(User user);

    void modifyUser(User user);

    UserResponseDTO loadUserByUserId(String userId);

    boolean checkUserId(String userId);
}
