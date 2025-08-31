package com.mercadolibre.itemdetail.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.itemdetail.model.Seller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class SellerRepository {
    
    private final ObjectMapper objectMapper;
    private List<Seller> sellers;
    
    public SellerRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/sellers.json");
        try (InputStream inputStream = resource.getInputStream()) {
            sellers = objectMapper.readValue(inputStream, new TypeReference<List<Seller>>() {});
        }
    }
    
    public Optional<Seller> findById(String id) {
        return sellers.stream()
                .filter(seller -> seller.getId().equals(id))
                .findFirst();
    }
    
    public List<Seller> findAll() {
        return sellers;
    }
}

