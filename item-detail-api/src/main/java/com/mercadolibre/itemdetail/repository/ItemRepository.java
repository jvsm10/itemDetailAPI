package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Item;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {
    
    private final ObjectMapper objectMapper;
    private List<Item> items;
    
    public ItemRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/items.json");
        try (InputStream inputStream = resource.getInputStream()) {
            items = objectMapper.readValue(inputStream, new TypeReference<List<Item>>() {});
        }
    }
    
    public Optional<Item> findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }
    
    public List<Item> findAll() {
        return items;
    }
}

