package com.bookrewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("transaction_type")
    private TransactionType transactionType;
    
    @Column("amount")
    private BigDecimal amount = BigDecimal.ZERO;
    
    @Column("points_change")
    private Integer pointsChange = 0;
    
    @Column("description")
    private String description;
    
    @Column("book_id")
    private UUID bookId;
    
    @Column("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum TransactionType {
        PURCHASE,
        REFUND,
        POINTS_EARNED,
        POINTS_SPENT,
        BALANCE_ADDED
    }
    
    // Constructor for balance transactions
    public Transaction(UUID userId, TransactionType transactionType, BigDecimal amount, String description) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor for points transactions
    public Transaction(UUID userId, TransactionType transactionType, Integer pointsChange, String description) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.pointsChange = pointsChange;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor for book purchases
    public Transaction(UUID userId, TransactionType transactionType, BigDecimal amount, 
                      Integer pointsChange, String description, UUID bookId) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.pointsChange = pointsChange;
        this.description = description;
        this.bookId = bookId;
        this.createdAt = LocalDateTime.now();
    }
} 