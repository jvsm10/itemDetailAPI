package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Review;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository {
    
    private final ObjectMapper objectMapper;
    private List<Review> reviews;
    
    public ReviewRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/reviews.json");
        try (InputStream inputStream = resource.getInputStream()) {
            reviews = objectMapper.readValue(inputStream, new TypeReference<List<Review>>() {});
        }
    }
    
    public List<Review> findByItemId(String itemId) {
        return reviews.stream()
                .filter(review -> review.getItemId().equals(itemId))
                .collect(Collectors.toList());
    }
    
    public List<Review> findAll() {
        return reviews;
    }
}

