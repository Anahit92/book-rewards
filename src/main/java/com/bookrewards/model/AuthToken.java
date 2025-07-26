package com.bookrewards.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("auth_tokens")
@Data
@NoArgsConstructor
public class AuthToken {
    
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("token")
    private String token;
    
    @Column("token_type")
    private String tokenType = "JWT";
    
    @Column("is_revoked")
    private boolean isRevoked = false;
    
    @Column("expires_at")
    private LocalDateTime expiresAt;
    
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Custom constructor
    public AuthToken(UUID userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.tokenType = "JWT";
        this.isRevoked = false;
        this.createdAt = LocalDateTime.now();
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    public boolean isValid() {
        return !isRevoked && !isExpired();
    }
} 