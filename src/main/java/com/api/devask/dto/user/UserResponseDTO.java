package com.api.devask.dto.user;

import com.api.devask.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String userName;
    private String email;

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
            user.getUserId(),
            user.getUserName(),
            user.getEmail()
        );
    }
}
