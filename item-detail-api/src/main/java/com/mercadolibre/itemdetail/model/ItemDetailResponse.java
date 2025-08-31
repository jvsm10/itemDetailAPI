package com.mercadolibre.itemdetail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponse {
    private Item item;
    private Seller seller;
    private List<Review> reviews;
    private Double averageRating;
    private Integer totalReviews;
}

