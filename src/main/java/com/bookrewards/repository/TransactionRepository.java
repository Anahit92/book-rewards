package com.bookrewards.repository;

import com.bookrewards.model.Transaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    
    @Query("SELECT * FROM transactions WHERE user_id = :userId ORDER BY created_at DESC")
    List<Transaction> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId);
    
    @Query("SELECT * FROM transactions WHERE user_id = :userId AND transaction_type = :transactionType ORDER BY created_at DESC")
    List<Transaction> findByUserIdAndTransactionTypeOrderByCreatedAtDesc(
            @Param("userId") UUID userId, 
            @Param("transactionType") Transaction.TransactionType transactionType);
    
    @Query("SELECT * FROM transactions WHERE book_id = :bookId ORDER BY created_at DESC")
    List<Transaction> findByBookIdOrderByCreatedAtDesc(@Param("bookId") UUID bookId);
} 