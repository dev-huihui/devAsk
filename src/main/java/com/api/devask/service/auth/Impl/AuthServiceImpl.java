package com.api.devask.service.auth.Impl;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginRequestDTO;
import com.api.devask.dto.auth.LoginResponseDTO;
import com.api.devask.repository.user.UserRepository;
import com.api.devask.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.devask.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secretKey;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Override
    @Transactional
    public void signUp(User user) {
        // 2026.04.21 아이디 중복체크
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 2026.04.13 비밀번호 암호화 처리
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 2026.04.21 역할 설정 > 회원가입 로직이기에 USER로 고정
        user.setRole("USER");

        // 2026.01.28 user 정보 저장
        userRepository.save(user);
    }

    // 2026.04.17 로그인 기능(아이디, 패스워드)
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // 2026.04.17 입력받은 아이디로 사용자 조회
        User confirmUser = userRepository.findById(loginRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2026.04.17 입력받은 패스워드와 저장된 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(loginRequest.getPassword(), confirmUser.getPassword())) {
            // 2026.04.20 비밀번호 불일치 시 실패 횟수 증가 후 저장
            confirmUser.setFailCnt(confirmUser.getFailCnt() + 1);
            userRepository.save(confirmUser);
            // 2026.04.17 비밀번호 불일치 예외처리
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 2026.04.17 로그인 성공 시 JWT 토큰 생성
        String token = jwtUtil.generateToken(confirmUser.getUserId());

        /*
         * 2026.04.17 만료시간 계산 (문자열로 변환)
         * JwtUtil의 expiration이 private이므로, 외부에서 알 수 있도록 JwtUtil에 getter를 추가하거나
         * 간단히 토큰에서 만료시간을 추출할 수도 있지만, 일단 임시로 직접 계산해서 반환하는 방식 사용
         * 현재 JwtUtil의 expiration을 가져오는 메소드가 없으므로, 향후 리팩토링이 필요
         */
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expireDateString = sdf.format(expirationDate);

        return LoginResponseDTO.of(confirmUser.getUserId(), token, expireDateString);
    }
}
