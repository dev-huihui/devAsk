package com.api.devask.dto.auth;

import lombok.Getter;
import lombok.Setter;

// 2026.04.20 로그인 요청 DTO
@Getter
@Setter
public class LoginRequestDTO {
    private String userId;
    private String password;
}
