package com.mercadolibre.itemdetail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemDetailApiApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }

    @Test
    void healthEndpoint_ShouldReturnOk() {
        // Given
        String url = "http://localhost:" + port + "/api/items/health";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("API is running!", response.getBody());
    }

    @Test
    void getAllItems_ShouldReturnItems() {
        // Given
        String url = "http://localhost:" + port + "/api/items";

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("MLB123456789"));
        assertTrue(response.getBody().contains("Smartphone Samsung Galaxy S24 Ultra"));
    }

    @Test
    void getItemDetail_ShouldReturnItemDetail_WhenItemExists() {
        // Given
        String itemId = "MLB123456789";
        String url = "http://localhost:" + port + "/api/items/" + itemId;

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"id\":\"" + itemId + "\""));
        assertTrue(response.getBody().contains("\"seller\""));
        assertTrue(response.getBody().contains("\"reviews\""));
        assertTrue(response.getBody().contains("\"averageRating\""));
        assertTrue(response.getBody().contains("\"totalReviews\""));
    }

    @Test
    void getItemDetail_ShouldReturnNotFound_WhenItemDoesNotExist() {
        // Given
        String itemId = "NONEXISTENT";
        String url = "http://localhost:" + port + "/api/items/" + itemId;

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getItemDetail_ShouldReturnCorrectStructure_ForAllTestItems() {
        // Test all items in the test data
        String[] itemIds = {"MLB123456789", "MLB987654321", "MLB555666777"};

        for (String itemId : itemIds) {
            // Given
            String url = "http://localhost:" + port + "/api/items/" + itemId;

            // When
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode(), "Failed for item: " + itemId);
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("\"item\""), "Missing item field for: " + itemId);
            assertTrue(response.getBody().contains("\"seller\""), "Missing seller field for: " + itemId);
            assertTrue(response.getBody().contains("\"reviews\""), "Missing reviews field for: " + itemId);
            assertTrue(response.getBody().contains("\"averageRating\""), "Missing averageRating field for: " + itemId);
            assertTrue(response.getBody().contains("\"totalReviews\""), "Missing totalReviews field for: " + itemId);
        }
    }
}

