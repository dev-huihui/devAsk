package com.api.devask.repository.sample;

import com.api.devask.domain.sample.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
