package com.api.devask.service.user;

import com.api.devask.domain.user.User;

public interface UserService {
    void addUser(User user);

    void modifyUser(User user);
}
