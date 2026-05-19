package com.api.devask.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Jackson 객체 생성을 위해 PUBLIC으로 변경
@Entity
@Table(name = "T_USER")
@DynamicInsert
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
    @Column(name = "FAIL_CNT", columnDefinition = "integer default 0")
    private Integer failCnt = 0;

    @Column(name = "ACCOUNT_LOCK", columnDefinition = "varchar(1) default 'N'")
    private String accountLock = "N";

    @Column(name = "USE_YN", columnDefinition = "varchar(1) default 'Y'")
    private String useYn = "Y";

    @Column(name = "DELETE_YN", columnDefinition = "varchar(1) default 'N'")
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
