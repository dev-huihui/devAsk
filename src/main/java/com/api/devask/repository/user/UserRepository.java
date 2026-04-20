package com.api.devask.repository.user;

import com.api.devask.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    // 2026.04.20 아이디 중복 체크
    boolean existsByUserId(String userId);
}
