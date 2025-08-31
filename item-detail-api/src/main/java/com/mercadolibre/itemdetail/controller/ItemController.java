package com.mercadolibre.itemdetail.controller;

import com.mercadolibre.itemdetail.model.Item;
import com.mercadolibre.itemdetail.model.ItemDetailResponse;
import com.mercadolibre.itemdetail.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {
    
    private final ItemService itemService;
    
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailResponse> getItemDetail(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<ItemDetailResponse> itemDetail = itemService.getItemDetail(id);
        
        if (itemDetail.isPresent()) {
            return ResponseEntity.ok(itemDetail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API is running!");
    }
}

