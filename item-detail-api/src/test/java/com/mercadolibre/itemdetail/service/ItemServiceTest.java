package com.mercadolibre.itemdetail.service;

import com.mercadolibre.itemdetail.model.Item;
import com.mercadolibre.itemdetail.model.ItemDetailResponse;
import com.mercadolibre.itemdetail.model.Review;
import com.mercadolibre.itemdetail.model.Seller;
import com.mercadolibre.itemdetail.repository.ItemRepository;
import com.mercadolibre.itemdetail.repository.ReviewRepository;
import com.mercadolibre.itemdetail.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ReviewRepository reviewRepository;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository, sellerRepository, reviewRepository);
    }

    @Test
    void getItemDetail_ShouldReturnCompleteItemDetail_WhenItemExists() {
        // Given
        String itemId = "MLB123456789";
        Item item = new Item(itemId, "Test Item", "Description", 100.0, "BRL", "Category", "SELLER001", 10, "image.jpg");
        Seller seller = new Seller("SELLER001", "Test Seller", 4.5, "Address");
        List<Review> reviews = Arrays.asList(
                new Review("REV001", itemId, "USER001", 5, "Great!", "2024-08-01"),
                new Review("REV002", itemId, "USER002", 4, "Good", "2024-08-02")
        );

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(sellerRepository.findById("SELLER001")).thenReturn(Optional.of(seller));
        when(reviewRepository.findByItemId(itemId)).thenReturn(reviews);

        // When
        Optional<ItemDetailResponse> result = itemService.getItemDetail(itemId);

        // Then
        assertTrue(result.isPresent());
        ItemDetailResponse response = result.get();
        assertEquals(item, response.getItem());
        assertEquals(seller, response.getSeller());
        assertEquals(reviews, response.getReviews());
        assertEquals(4.5, response.getAverageRating());
        assertEquals(2, response.getTotalReviews());

        verify(itemRepository).findById(itemId);
        verify(sellerRepository).findById("SELLER001");
        verify(reviewRepository).findByItemId(itemId);
    }

    @Test
    void getItemDetail_ShouldReturnEmpty_WhenItemDoesNotExist() {
        // Given
        String itemId = "NONEXISTENT";
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // When
        Optional<ItemDetailResponse> result = itemService.getItemDetail(itemId);

        // Then
        assertFalse(result.isPresent());
        verify(itemRepository).findById(itemId);
        verifyNoInteractions(sellerRepository, reviewRepository);
    }

    @Test
    void getItemDetail_ShouldHandleNoSeller_WhenSellerDoesNotExist() {
        // Given
        String itemId = "MLB123456789";
        Item item = new Item(itemId, "Test Item", "Description", 100.0, "BRL", "Category", "SELLER001", 10, "image.jpg");
        List<Review> reviews = Collections.emptyList();

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(sellerRepository.findById("SELLER001")).thenReturn(Optional.empty());
        when(reviewRepository.findByItemId(itemId)).thenReturn(reviews);

        // When
        Optional<ItemDetailResponse> result = itemService.getItemDetail(itemId);

        // Then
        assertTrue(result.isPresent());
        ItemDetailResponse response = result.get();
        assertEquals(item, response.getItem());
        assertNull(response.getSeller());
        assertEquals(reviews, response.getReviews());
        assertEquals(0.0, response.getAverageRating());
        assertEquals(0, response.getTotalReviews());
    }

    @Test
    void getItemDetail_ShouldCalculateCorrectAverageRating() {
        // Given
        String itemId = "MLB123456789";
        Item item = new Item(itemId, "Test Item", "Description", 100.0, "BRL", "Category", "SELLER001", 10, "image.jpg");
        List<Review> reviews = Arrays.asList(
                new Review("REV001", itemId, "USER001", 5, "Great!", "2024-08-01"),
                new Review("REV002", itemId, "USER002", 3, "OK", "2024-08-02"),
                new Review("REV003", itemId, "USER003", 4, "Good", "2024-08-03")
        );

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(sellerRepository.findById("SELLER001")).thenReturn(Optional.empty());
        when(reviewRepository.findByItemId(itemId)).thenReturn(reviews);

        // When
        Optional<ItemDetailResponse> result = itemService.getItemDetail(itemId);

        // Then
        assertTrue(result.isPresent());
        ItemDetailResponse response = result.get();
        assertEquals(4.0, response.getAverageRating()); // (5+3+4)/3 = 4.0
        assertEquals(3, response.getTotalReviews());
    }

    @Test
    void getAllItems_ShouldReturnAllItems() {
        // Given
        List<Item> items = Arrays.asList(
                new Item("1", "Item 1", "Desc 1", 100.0, "BRL", "Cat 1", "SELLER001", 10, "img1.jpg"),
                new Item("2", "Item 2", "Desc 2", 200.0, "BRL", "Cat 2", "SELLER002", 20, "img2.jpg")
        );
        when(itemRepository.findAll()).thenReturn(items);

        // When
        List<Item> result = itemService.getAllItems();

        // Then
        assertEquals(items, result);
        verify(itemRepository).findAll();
    }
}

