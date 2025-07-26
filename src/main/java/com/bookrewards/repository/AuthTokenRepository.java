package com.bookrewards.repository;

import com.bookrewards.model.AuthToken;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken, UUID> {
    
    @Query("SELECT * FROM auth_tokens WHERE user_id = :userId AND is_revoked = false AND expires_at > :now")
    List<AuthToken> findValidTokensByUserId(@Param("userId") UUID userId, @Param("now") LocalDateTime now);
    
    @Query("SELECT * FROM auth_tokens WHERE token = :token AND is_revoked = false AND expires_at > :now")
    Optional<AuthToken> findValidTokenByToken(@Param("token") String token, @Param("now") LocalDateTime now);
    
    @Query("SELECT * FROM auth_tokens WHERE user_id = :userId")
    List<AuthToken> findAllByUserId(@Param("userId") UUID userId);
    
    @Query("UPDATE auth_tokens SET is_revoked = true WHERE user_id = :userId")
    void revokeAllTokensByUserId(@Param("userId") UUID userId);
    
    @Query("UPDATE auth_tokens SET is_revoked = true WHERE token = :token")
    int revokeToken(@Param("token") String token);
    
    @Query("DELETE FROM auth_tokens WHERE expires_at < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
} 
