package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewRepositoryTest {

    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        reviewRepository = new ReviewRepository(objectMapper);
        reviewRepository.loadData();
    }

    @Test
    void findByItemId_ShouldReturnReviews_WhenItemHasReviews() {
        // Given
        String itemId = "MLB123456789";

        // When
        List<Review> result = reviewRepository.findByItemId(itemId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(review -> itemId.equals(review.getItemId())));
    }

    @Test
    void findByItemId_ShouldReturnEmptyList_WhenItemHasNoReviews() {
        // Given
        String itemId = "NONEXISTENT";

        // When
        List<Review> result = reviewRepository.findByItemId(itemId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ShouldReturnAllReviews() {
        // When
        List<Review> result = reviewRepository.findAll();

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());
    }

    @Test
    void findByItemId_ShouldReturnCorrectReviewDetails() {
        // Given
        String itemId = "MLB987654321";

        // When
        List<Review> result = reviewRepository.findByItemId(itemId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        Review firstReview = result.get(0);
        assertEquals(itemId, firstReview.getItemId());
        assertTrue(firstReview.getRating() >= 1 && firstReview.getRating() <= 5);
        assertNotNull(firstReview.getComment());
        assertNotNull(firstReview.getDate());
    }
}

