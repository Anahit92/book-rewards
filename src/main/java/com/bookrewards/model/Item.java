package com.bookrewards.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    private UUID id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    @Column("title")
    private String title;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column("description")
    private String description;
    
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column("user_id")
    private UUID userId;
    
} 