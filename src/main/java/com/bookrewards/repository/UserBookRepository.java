package com.bookrewards.repository;

import com.bookrewards.model.UserBook;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserBookRepository extends CrudRepository<UserBook, UUID> {
    
    @Query("SELECT * FROM user_books WHERE user_id = :userId ORDER BY purchase_date DESC")
    List<UserBook> findByUserIdOrderByPurchaseDateDesc(@Param("userId") UUID userId);
    
    @Query("SELECT * FROM user_books WHERE user_id = :userId AND book_id = :bookId")
    Optional<UserBook> findByUserIdAndBookId(@Param("userId") UUID userId, @Param("bookId") UUID bookId);
    
    @Query("SELECT EXISTS(SELECT 1 FROM user_books WHERE user_id = :userId AND book_id = :bookId)")
    boolean existsByUserIdAndBookId(@Param("userId") UUID userId, @Param("bookId") UUID bookId);
    
    @Query("SELECT COUNT(*) FROM user_books WHERE user_id = :userId")
    long countByUserId(@Param("userId") UUID userId);
} 