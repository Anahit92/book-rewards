package com.bookrewards.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {
    
    @GetMapping("/upload")
    public String uploadPage() {
        return "admin-upload";
    }
    
    @GetMapping("/books")
    public String manageBooksPage() {
        return "admin-books"; // You can create this page later
    }
} 