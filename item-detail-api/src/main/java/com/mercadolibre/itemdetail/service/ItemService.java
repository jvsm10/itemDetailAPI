package com.mercadolibre.itemdetail.service;

import com.mercadolibre.itemdetail.model.Item;
import com.mercadolibre.itemdetail.model.ItemDetailResponse;
import com.mercadolibre.itemdetail.model.Review;
import com.mercadolibre.itemdetail.model.Seller;
import com.mercadolibre.itemdetail.repository.ItemRepository;
import com.mercadolibre.itemdetail.repository.ReviewRepository;
import com.mercadolibre.itemdetail.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;
    private final ReviewRepository reviewRepository;
    
    public ItemService(ItemRepository itemRepository, 
                      SellerRepository sellerRepository, 
                      ReviewRepository reviewRepository) {
        this.itemRepository = itemRepository;
        this.sellerRepository = sellerRepository;
        this.reviewRepository = reviewRepository;
    }
    
    public Optional<ItemDetailResponse> getItemDetail(String itemId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        
        if (itemOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Item item = itemOpt.get();
        
        // Buscar vendedor
        Optional<Seller> sellerOpt = sellerRepository.findById(item.getSellerId());
        Seller seller = sellerOpt.orElse(null);
        
        // Buscar avaliações
        List<Review> reviews = reviewRepository.findByItemId(itemId);
        
        // Calcular média das avaliações
        Double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        
        Integer totalReviews = reviews.size();
        
        ItemDetailResponse response = new ItemDetailResponse(
                item, seller, reviews, averageRating, totalReviews
        );
        
        return Optional.of(response);
    }
    
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}

