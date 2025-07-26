package com.bookrewards.service;

import com.bookrewards.model.AuthToken;
import com.bookrewards.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthTokenService {
    
    private final AuthTokenRepository authTokenRepository;
    
    /**
     * Save a new authentication token for a user
     */
    public AuthToken saveToken(UUID userId, String token, LocalDateTime expiresAt) {
        AuthToken authToken = new AuthToken(userId, token, expiresAt);
        AuthToken savedToken = authTokenRepository.save(authToken);
        log.info("Saved auth token for user: {}", userId);
        return savedToken;
    }
    
    /**
     * Find a valid token by token string
     */
    public Optional<AuthToken> findValidToken(String token) {
        return authTokenRepository.findValidTokenByToken(token, LocalDateTime.now());
    }
    
    /**
     * Get all valid tokens for a user
     */
    public List<AuthToken> getValidTokensByUserId(UUID userId) {
        return authTokenRepository.findValidTokensByUserId(userId, LocalDateTime.now());
    }
    
    /**
     * Revoke a specific token
     */
    public int revokeToken(String token) {
        String rawToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        int updated = authTokenRepository.revokeToken(rawToken);
        log.info("Revoked auth token, rows updated: {}", updated);
        return updated;
    }
    
    /**
     * Revoke all tokens for a user
     */
    public void revokeAllTokensByUserId(UUID userId) {
        authTokenRepository.revokeAllTokensByUserId(userId);
        log.info("Revoked all auth tokens for user: {}", userId);
    }
    
    /**
     * Check if a token is valid (exists and not revoked/expired)
     */
    public boolean isTokenValid(String token) {
        Optional<AuthToken> authToken = findValidToken(token);
        return authToken.isPresent() && authToken.get().isValid();
    }
    
    /**
     * Clean up expired tokens (scheduled task)
     */
    @Scheduled(cron = "0 0 2 * * ?") // Run at 2 AM every day
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        authTokenRepository.deleteExpiredTokens(now);
        log.info("Cleaned up expired auth tokens");
    }
} 