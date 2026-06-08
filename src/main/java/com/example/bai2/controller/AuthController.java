package com.example.bai2.controller;

import com.example.bai2.model.dto.request.AuthRequest;
import com.example.bai2.model.dto.request.RefreshTokenRequest;
import com.example.bai2.model.dto.response.JWTResponse;
import com.example.bai2.model.entity.Account;
import com.example.bai2.model.entity.TokenSession;
import com.example.bai2.service.AuthService;
import com.example.bai2.service.TokenSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gallery/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenSessionService tokenSessionService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.register(authRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return new ResponseEntity<>(tokenSessionService.refreshToken(request.getToken()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<TokenSession> revokeToken(@RequestBody RefreshTokenRequest request) {
        return new ResponseEntity<>(tokenSessionService.revokeToken(request.getToken()), HttpStatus.OK);
    }
}
