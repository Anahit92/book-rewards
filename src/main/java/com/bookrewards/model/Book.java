package com.bookrewards.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    private UUID id;
    
    @NotBlank(message = "Title is required")
    @Column("title")
    private String title;
    
    @Column("author")
    private String author;
    
    @Column("description")
    private String description;
    
    @Column("isbn")
    private String isbn;

    @Column("points_cost")
    private Integer pointsCost = 0;
    
    @Column("file_size")
    private Long fileSize;
    
    @Column("file_type")
    private String fileType;
    
    @Column("file_path")
    private String filePath;
    
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column("updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Constructor without binary data for listing/searching
    public Book(String title, String author, String description, String isbn, 
                Integer pointsCost, Long fileSize, String fileType) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.pointsCost = pointsCost;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
} 