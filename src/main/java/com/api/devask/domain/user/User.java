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
@Table(name = "T_USER")
public class User {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ROLE", nullable = false)
    private String role;

    // 2026.04.20 부가적인 기능(로그인 실패횟수, 계정잠금)
    @Column(name = "FAIL_CNT", nullable = false)
    private Integer failCnt = 0;

    @Column(name = "ACCOUNT_LOCK", nullable = false)
    private String accountLock = "N";

    @Column(name = "USE_YN", nullable = false)
    private String useYn = "Y";

    @Column(name = "DELETE_YN", nullable = false)
    private String deleteYn = "N";

    @PrePersist
    public void prePersist() {
        if (this.failCnt == null) {
            this.failCnt = 0;
        }

        if (this.accountLock == null) {
            this.accountLock = "N";
        }

        if (this.useYn == null) {
            this.useYn = "Y";
        }

        if (this.deleteYn == null) {
            this.deleteYn = "N";
        }
    }
}
