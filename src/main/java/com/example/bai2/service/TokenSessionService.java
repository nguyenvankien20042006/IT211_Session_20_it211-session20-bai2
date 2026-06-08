package com.example.bai2.service;

import com.example.bai2.model.dto.response.JWTResponse;
import com.example.bai2.model.entity.TokenSession;
import com.example.bai2.repository.TokenSessionRepository;
import com.example.bai2.security.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenSessionService {
    private final TokenSessionRepository tokenSessionRepository;
    private final JWTProvider jWTProvider;

    public TokenSession generateTokenSession(String username) {
        TokenSession tokenSession = TokenSession.builder().refreshTokenValue(UUID.randomUUID().toString()).username(username).isRevoked(false).isExpired(false).build();
        return tokenSessionRepository.save(tokenSession);
    }

    public TokenSession getTokenSession(String token) {
        return tokenSessionRepository.findByRefreshTokenValue(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public boolean verifyToken(String token) {
        TokenSession tokenSession = generateTokenSession(token);
        return !tokenSession.getIsRevoked() && !tokenSession.getIsExpired();
    }

    public TokenSession revokeToken(String token) {
        TokenSession tokenSession = getTokenSession(token);
        tokenSession.setIsRevoked(true);
        tokenSession.setIsExpired(true);
        return tokenSessionRepository.save(tokenSession);
    }

    public JWTResponse refreshToken(String token) {
        TokenSession tokenSession = getTokenSession(token);
        String newToken = jWTProvider.generateToken(tokenSession.getUsername());
        return JWTResponse.builder().accessToken(newToken).refreshToken(token).build();
    }
}
