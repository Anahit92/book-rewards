package com.bookrewards.service;

import com.bookrewards.model.Item;
import com.bookrewards.model.User;
import com.bookrewards.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;
    
    public List<Item> getAllItemsByUser(User user) {
        return itemRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
    
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
    
    public Optional<Item> getItemById(UUID id) {
        return itemRepository.findById(id);
    }
    
    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }
    
    public boolean isItemOwner(UUID itemId, User user) {
        Optional<Item> item = getItemById(itemId);
        return item.isPresent() && item.get().getUserId().equals(user.getId());
    }
    
    public Item createItem(Item item, User user) {
        item.setUserId(user.getId());
        return saveItem(item);
    }
    
    public Item updateItem(UUID id, Item item, User user) {
        if (!isItemOwner(id, user)) {
            throw new RuntimeException("You can only edit your own items");
        }
        
        Optional<Item> existingItemOpt = getItemById(id);
        if (existingItemOpt.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        
        Item existingItem = existingItemOpt.get();
        existingItem.setTitle(item.getTitle());
        existingItem.setDescription(item.getDescription());
        return saveItem(existingItem);
    }
    
    public void deleteItemWithOwnershipCheck(UUID id, User user) {
        if (!isItemOwner(id, user)) {
            throw new RuntimeException("You can only delete your own items");
        }
        deleteItem(id);
    }
    
    public Item getItemByIdWithOwnershipCheck(UUID id, User user) {
        if (!isItemOwner(id, user)) {
            throw new RuntimeException("You can only view your own items");
        }
        
        Optional<Item> item = getItemById(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item not found");
        }
        
        return item.get();
    }
} 