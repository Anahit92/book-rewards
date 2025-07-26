package com.bookrewards.controller;

import com.bookrewards.model.Book;
import com.bookrewards.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final BookService bookService;
    
    @PostMapping("/books/upload")
    public ResponseEntity<?> uploadBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("pointsCost") Integer pointsCost,
            @RequestParam("file") MultipartFile file) {
        
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setIsbn(isbn);
            book.setPointsCost(pointsCost);
            
            Book savedBook = bookService.uploadBookWithFile(book, file);
            
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        try {
            bookService.deleteBook(java.util.UUID.fromString(id));
            return ResponseEntity.ok().body("Book deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting book: " + e.getMessage());
        }
    }
} 