package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SellerRepositoryTest {

    private SellerRepository sellerRepository;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        sellerRepository = new SellerRepository(objectMapper);
        sellerRepository.loadData();
    }

    @Test
    void findById_ShouldReturnSeller_WhenSellerExists() {
        // Given
        String sellerId = "SELLER001";

        // When
        Optional<Seller> result = sellerRepository.findById(sellerId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(sellerId, result.get().getId());
        assertEquals("TechStore Premium", result.get().getName());
        assertEquals(4.8, result.get().getRating());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenSellerDoesNotExist() {
        // Given
        String sellerId = "NONEXISTENT";

        // When
        Optional<Seller> result = sellerRepository.findById(sellerId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllSellers() {
        // When
        List<Seller> result = sellerRepository.findAll();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(seller -> "SELLER001".equals(seller.getId())));
        assertTrue(result.stream().anyMatch(seller -> "SELLER002".equals(seller.getId())));
        assertTrue(result.stream().anyMatch(seller -> "SELLER003".equals(seller.getId())));
    }
}

