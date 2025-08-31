package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        itemRepository = new ItemRepository(objectMapper);
        itemRepository.loadData();
    }

    @Test
    void findById_ShouldReturnItem_WhenItemExists() {
        // Given
        String itemId = "MLB123456789";

        // When
        Optional<Item> result = itemRepository.findById(itemId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(itemId, result.get().getId());
        assertEquals("Smartphone Samsung Galaxy S24 Ultra 256GB", result.get().getName());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenItemDoesNotExist() {
        // Given
        String itemId = "NONEXISTENT";

        // When
        Optional<Item> result = itemRepository.findById(itemId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllItems() {
        // When
        List<Item> result = itemRepository.findAll();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(item -> "MLB123456789".equals(item.getId())));
        assertTrue(result.stream().anyMatch(item -> "MLB987654321".equals(item.getId())));
        assertTrue(result.stream().anyMatch(item -> "MLB555666777".equals(item.getId())));
    }

    @Test
    void findById_ShouldReturnCorrectItemDetails() {
        // Given
        String itemId = "MLB987654321";

        // When
        Optional<Item> result = itemRepository.findById(itemId);

        // Then
        assertTrue(result.isPresent());
        Item item = result.get();
        assertEquals("Notebook Dell Inspiron 15 3000 Intel Core i5", item.getName());
        assertEquals(2899.90, item.getPrice());
        assertEquals("BRL", item.getCurrency());
        assertEquals("SELLER002", item.getSellerId());
    }
}

