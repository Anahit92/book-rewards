package com.bookrewards.repository;

import com.bookrewards.model.Item;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends CrudRepository<Item, UUID> {
    
    @Query("SELECT * FROM items WHERE user_id = :userId ORDER BY created_at DESC")
    List<Item> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId);
} 