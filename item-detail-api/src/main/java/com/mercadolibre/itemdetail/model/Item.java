package com.mercadolibre.itemdetail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String currency;
    private String category;
    private String sellerId;
    private Integer availableQuantity;
    private String imageUrl;
}

