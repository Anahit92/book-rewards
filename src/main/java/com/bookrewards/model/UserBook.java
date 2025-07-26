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

@Table("user_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {
    
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("book_id")
    private UUID bookId;
    
    @Column("purchase_date")
    private LocalDateTime purchaseDate = LocalDateTime.now();
    
    @Column("points_spent")
    private Integer pointsSpent = 0;
    
    @Column("price_paid")
    private BigDecimal pricePaid = BigDecimal.ZERO;
    
    // Constructor for new purchase
    public UserBook(UUID userId, UUID bookId, Integer pointsSpent, BigDecimal pricePaid) {
        this.userId = userId;
        this.bookId = bookId;
        this.pointsSpent = pointsSpent;
        this.pricePaid = pricePaid;
        this.purchaseDate = LocalDateTime.now();
    }
} 