package com.api.devask.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 2026.04.20 로그인 응답 DTO
@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String userId;
    private String token;
    private String expireDate;

    public static LoginResponseDTO of(String userId, String token, String expireDate) {
        return new LoginResponseDTO(userId, token, expireDate);
    }
}
