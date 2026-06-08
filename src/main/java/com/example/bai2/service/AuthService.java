package com.example.bai2.service;

import com.example.bai2.model.dto.request.AuthRequest;
import com.example.bai2.model.dto.response.JWTResponse;
import com.example.bai2.model.entity.Account;
import com.example.bai2.model.entity.Role;
import com.example.bai2.model.entity.TokenSession;
import com.example.bai2.repository.AccountRepository;
import com.example.bai2.security.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jWTProvider;
    private final TokenSessionService tokenSessionService;
    private final PasswordEncoder passwordEncoder;

    public Account register(AuthRequest authRequest) {
        Account account = Account.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Set.of(Role.builder().id(1L).build()))
                .isActive(true)
                .build();
        return accountRepository.save(account);
    }

    public JWTResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jWTProvider.generateToken(authRequest.getUsername());
        TokenSession refreshToken = tokenSessionService.generateTokenSession(authRequest.getUsername());
        return JWTResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getRefreshTokenValue()).build();
    }
}
