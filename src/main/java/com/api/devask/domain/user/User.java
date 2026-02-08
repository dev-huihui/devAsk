package com.api.devask.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="T_USER")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name="USER_PW", nullable = false)
    private String password;

    @Column(name="USER_NAME", nullable = false)
    private String userName;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="AUTH", nullable = false)
    private String auth;
}
