package com.api.devask.service.auth.Impl;

import com.api.devask.domain.user.User;
import com.api.devask.dto.auth.LoginResponseDTO;
import com.api.devask.repository.user.UserRepository;
import com.api.devask.service.auth.AuthService;

import io.jsonwebtoken.Jwts;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.devask.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 2026.01.28 user 정보를 저장하는 메소드
    @Override
    @Transactional
    public void signUp(User user) {
        // 2026.04.13 비밀번호 암호화 처리
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 2026.01.28 user 정보 저장
        userRepository.save(user);
    }

    // 2026.04.17 로그인 기능추가
    @Override
    public LoginResponseDTO login(User user) {
        // 1. 아이디로 사용자 조회
        User findUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2. 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 성공 시 JWT 토큰 생성
        String token = jwtUtil.generateToken(findUser.getUserId());

        // 4. 만료시간 계산 (문자열로 변환)
        // JwtUtil의 expiration이 private이므로, 외부에서 알 수 있도록 JwtUtil에 getter를 추가하거나
        // 간단히 토큰에서 만료시간을 추출할 수도 있지만, 일단 임시로 직접 계산해서 반환하는 방식을 씁니다.
        // 현재 JwtUtil의 expiration을 가져오는 메소드가 없으므로, 향후 리팩토링이 필요할 수 있습니다.
        Date expirationDate = Jwts.parser().setSigningKey("devAskSecretKey").parseClaimsJws(token).getBody()
                .getExpiration();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expireDateString = sdf.format(expirationDate);

        return LoginResponseDTO.of(findUser.getUserId(), token, expireDateString);
    }
}
