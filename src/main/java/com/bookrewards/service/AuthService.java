package com.bookrewards.service;

import com.bookrewards.config.JwtUtil;
import com.bookrewards.dto.AuthRequest;
import com.bookrewards.dto.AuthResponse;
import com.bookrewards.dto.UserRegistrationRequest;
import com.bookrewards.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthTokenService authTokenService;
    
    public AuthResponse registerUser(UserRegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
        User savedUser = userService.registerUser(user);
        
        // Generate token for the newly registered user
        UserDetails userDetails = userService.loadUserByUsername(savedUser.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        // Save the token to the database
        authTokenService.saveToken(savedUser.getId(), token, jwtUtil.extractExpirationAsLocalDateTime(token));
        
        return new AuthResponse(token, savedUser.getUsername());
    }
    
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        // Save the token to the database
        User user = userService.findByUsername(request.getUsername());
        authTokenService.saveToken(user.getId(), token, jwtUtil.extractExpirationAsLocalDateTime(token));
        
        return new AuthResponse(token, request.getUsername());
    }
    
    public Map<String, String> logout(String token) {
        String jwt = token.substring(7);
        // Revoke the token
        int updated = authTokenService.revokeToken(jwt);
        return Map.of("message", "Successfully logged out");
    }
    
    public Map<String, Object> getCurrentUser(String token) {
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userService.findByUsername(username);
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());
        
        return userInfo;
    }
} 