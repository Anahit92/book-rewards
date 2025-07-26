package com.bookrewards.repository;

import com.bookrewards.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    
    @Query("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUsername(@Param("username") String username);
    
    @Query("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    
    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    long countByUsername(@Param("username") String username);
    
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    long countByEmail(@Param("email") String email);
    
    default boolean existsByUsername(String username) {
        return countByUsername(username) > 0;
    }
    
    default boolean existsByEmail(String email) {
        return countByEmail(email) > 0;
    }
} 