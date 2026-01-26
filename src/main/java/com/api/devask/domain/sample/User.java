package com.api.devask.domain.sample;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Table(name="T_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID", nullable = false, unique = true)
    private Long userId;

    @Column(name="USER_PW", nullable = false)
    private String password;

    @Column(name="USER_NAME", nullable = false)
    private String userName;

    @Column(name="EMAIL", nullable = false)
    private String email;

    public User(String name) {
    }

}
