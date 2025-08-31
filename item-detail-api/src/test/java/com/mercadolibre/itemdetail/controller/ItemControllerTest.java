package com.mercadolibre.itemdetail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Item;
import com.mercadolibre.itemdetail.model.ItemDetailResponse;
import com.mercadolibre.itemdetail.model.Review;
import com.mercadolibre.itemdetail.model.Seller;
import com.mercadolibre.itemdetail.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getItemDetail_ShouldReturnItemDetail_WhenItemExists() throws Exception {
        // Given
        String itemId = "MLB123456789";
        Item item = new Item(itemId, "Test Item", "Description", 100.0, "BRL", "Category", "SELLER001", 10, "image.jpg");
        Seller seller = new Seller("SELLER001", "Test Seller", 4.5, "Address");
        List<Review> reviews = Arrays.asList(
                new Review("REV001", itemId, "USER001", 5, "Great!", "2024-08-01")
        );
        ItemDetailResponse response = new ItemDetailResponse(item, seller, reviews, 5.0, 1);

        when(itemService.getItemDetail(itemId)).thenReturn(Optional.of(response));

        // When & Then
        mockMvc.perform(get("/api/items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.item.id").value(itemId))
                .andExpect(jsonPath("$.item.name").value("Test Item"))
                .andExpect(jsonPath("$.seller.name").value("Test Seller"))
                .andExpect(jsonPath("$.averageRating").value(5.0))
                .andExpect(jsonPath("$.totalReviews").value(1));
    }

    @Test
    void getItemDetail_ShouldReturnNotFound_WhenItemDoesNotExist() throws Exception {
        // Given
        String itemId = "NONEXISTENT";
        when(itemService.getItemDetail(itemId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/items/{id}", itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getItemDetail_ShouldReturnBadRequest_WhenItemIdIsBlank() throws Exception {
        // Given - usando um espaço em branco como ID
        String itemId = " ";

        // When & Then
        mockMvc.perform(get("/api/items/{id}", itemId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllItems_ShouldReturnAllItems() throws Exception {
        // Given
        List<Item> items = Arrays.asList(
                new Item("1", "Item 1", "Desc 1", 100.0, "BRL", "Cat 1", "SELLER001", 10, "img1.jpg"),
                new Item("2", "Item 2", "Desc 2", 200.0, "BRL", "Cat 2", "SELLER002", 20, "img2.jpg")
        );
        when(itemService.getAllItems()).thenReturn(items);

        // When & Then
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Item 2"));
    }

    @Test
    void getAllItems_ShouldReturnEmptyList_WhenNoItemsExist() throws Exception {
        // Given
        when(itemService.getAllItems()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void health_ShouldReturnHealthStatus() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/items/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("API is running!"));
    }
}

