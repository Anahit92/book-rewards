package com.bookrewards.repository;

import com.bookrewards.model.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {
    
    @Query("SELECT * FROM books WHERE title LIKE %:title%")
    List<Book> findByTitleContaining(@Param("title") String title);
    
    @Query("SELECT * FROM books WHERE author LIKE %:author%")
    List<Book> findByAuthorContaining(@Param("author") String author);
    
    @Query("SELECT * FROM books WHERE isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);
    
    @Query("SELECT * FROM books ORDER BY created_at DESC")
    List<Book> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT * FROM books WHERE points_cost <= :maxPoints ORDER BY points_cost ASC")
    List<Book> findByPointsCostLessThanEqualOrderByPointsCostAsc(@Param("maxPoints") Integer maxPoints);
} 