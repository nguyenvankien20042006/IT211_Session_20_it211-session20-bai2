package com.example.bai2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshTokenValue;
    private Boolean isRevoked;
    private Boolean isExpired;
    private String username;
}
