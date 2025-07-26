package com.bookrewards.service;

import com.bookrewards.model.Book;
import com.bookrewards.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final FileStorageService fileStorageService;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAllOrderByCreatedAtDesc();
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }
    
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    public List<Book> getBooksByPointsCost(Integer maxPoints) {
        return bookRepository.findByPointsCostLessThanEqualOrderByPointsCostAsc(maxPoints);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }
    
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
    
    public Book createBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        return saveBook(book);
    }
    
    public Book updateBook(UUID id, Book book) {
        Optional<Book> existingBookOpt = getBookById(id);
        if (existingBookOpt.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        
        Book existingBook = existingBookOpt.get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPointsCost(book.getPointsCost());
        existingBook.setFileSize(book.getFileSize());
        existingBook.setFileType(book.getFileType());
        existingBook.setFilePath(book.getFilePath());
        existingBook.setUpdatedAt(LocalDateTime.now());
        
        return saveBook(existingBook);
    }
    
    public Book getBookByIdOrThrow(UUID id) {
        Optional<Book> book = getBookById(id);
        if (book.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        return book.get();
    }
    
    public Book uploadBookWithFile(Book book, MultipartFile file) throws IOException {
        // Validate file type
        if (file.isEmpty()) {
            throw new RuntimeException("File cannot be empty");
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new RuntimeException("Only PDF files are allowed");
        }
        
        // Store the file
        String filename = fileStorageService.storeFile(file);
        
        // Set book properties
        book.setFileType("application/pdf");
        book.setFileSize(file.getSize());
        book.setFilePath(filename);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        // Ensure UUID is set
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }
        return saveBook(book);
    }
} 