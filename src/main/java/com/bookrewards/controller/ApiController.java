package com.bookrewards.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class ApiController {
    
    @GetMapping("/info")
    public Map<String, Object> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Book Rewards API");
        info.put("version", "1.0.0");
        info.put("description", "REST API for Book Rewards application");
        
        Map<String, Object> endpoints = new HashMap<>();
        
        Map<String, String> authEndpoints = new HashMap<>();
        authEndpoints.put("POST /api/auth/register", "Register a new user");
        authEndpoints.put("POST /api/auth/login", "Login user");
        authEndpoints.put("GET /api/auth/me", "Get current user info (requires authentication)");
        endpoints.put("Authentication", authEndpoints);
        
        Map<String, String> itemEndpoints = new HashMap<>();
        itemEndpoints.put("GET /api/items", "Get all items for current user");
        itemEndpoints.put("GET /api/items/{id}", "Get item by ID");
        itemEndpoints.put("POST /api/items", "Create new item");
        itemEndpoints.put("PUT /api/items/{id}", "Update item");
        itemEndpoints.put("DELETE /api/items/{id}", "Delete item");
        endpoints.put("Items", itemEndpoints);
        
        info.put("endpoints", endpoints);
        
        Map<String, String> authInfo = new HashMap<>();
        authInfo.put("type", "Bearer Token");
        authInfo.put("header", "Authorization: Bearer <your-jwt-token>");
        info.put("authentication", authInfo);
        
        return info;
    }
} 